import java.util.Iterator;
import java.util.LinkedList;


//here when we have a collision, we use a different hash function to find a new slot within the table
public class HashTableQuadraticProbing <K,V> implements Iterable<K> {
	
	//load factor is the ratio between the maximum threshold and the capacity
	private double loadFactor;
	//modificationCount is for the iterator
	private int capacity, threshold, modificationCount = 0;
	
	//used buckets is the total number of buckets being used including tombstones
	//keyCount is the total number of keys in the table
	private int usedBuckets = 0, keyCount = 0;
	
	//these arrays store the key-value pairs
	private K[] keyTable;
	private V[] valueTable;
	
	//flag used the indicate whether an item was found in the hash table 
	//just to avoid traversing through keys again for the contains method
	private boolean containsFlag = false;
	
	//Special marker to indicate the deletion of a key-value pair
	private final K TOMBSTONE = (K) (new Object());
	
	//default values
	private static final int DEFAULT_CAPACITY = 8;
	private static final double DEFAULT_LOAD_FACTOR = 0.45;
	
	public HashTableQuadraticProbing () {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	public HashTableQuadraticProbing (int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}
	
	//Designated Constructor
	//capacity must be greater than or equal to 0
	//maxLoadFactor must be greater than 0
	@SuppressWarnings("unchecked")
	public HashTableQuadraticProbing(int capacity, double loadFactor) {
		this.loadFactor = loadFactor;
		this.capacity = Math.max(DEFAULT_CAPACITY, next2Power(capacity));
		this.threshold = (int) (this.capacity * loadFactor);
		
		keyTable = (K[]) new Object[this.capacity];
		valueTable = (V[]) new Object[this.capacity];
	}
	
	//finds the next power of two above this value
	private static int next2Power(int n) {
		return Integer.highestOneBit(n) << 1;
	}
	
	//Quadratic Probing function (x^2+x)/2
	private static int P(int x) {
		return (x*x + x)/2 >> 1;
	}
	
	//returns a value that is within the bounds of the key and value table array
	private int normalizeIndex(int keyHash) {
		return (keyHash & 0x7FFFFFFF) % capacity;
	}
	
	//Clears the contents of the hash table
	public void clear() {
		for(int i = 0; i < capacity; i++) {
			keyTable[i] = null;
			valueTable[i] = null;
		}
		
		keyCount = usedBuckets = 0;
		modificationCount++;
	}
	
	public int size() {
		return keyCount;
	}
	
	public boolean isEmpty() {return keyCount == 0;}
	
	//insert, put, or add a key value pair into the hash table
	public V put(K key, V value) { return insert(key, value);}
	public V add(K key, V value) { return insert(key, value);}
	
	//Places a key-value pair into the hash-table. If the key already exists
	//update the value
	//returns the old value
	public V insert(K key, V value) {
		if(key == null) throw new IllegalArgumentException("Null key");
		if(usedBuckets >= threshold) resizeTable();
		
		final int hash = normalizeIndex(key.hashCode());
		//i is the current index in the hash table
		//j is the position of the first tombstone we encounter
		//x is the number of times we use the quadratic probing function
		int i = hash, j = -1, x = 1;
		
		//for every i we encounter we have to check for these conditions
		do {
			//current spot was previously deleted
			if(keyTable[i] == TOMBSTONE) {
				if (j == -1) j = 1;
				
			//the current cell contains a key
			} else if(keyTable[i] != null) {
				//if the current value equals the key
				if(keyTable[i].equals(key)) {
					V oldValue = valueTable[i];
					
					//if we haven't hit a tombstone
					if(j == -1) {
						valueTable[i] = value;
					//if we have, replace the first tombstone with the key and value
					} else {
						keyTable[i] = TOMBSTONE;
						valueTable[i] = null;
						keyTable[j] = key;
						valueTable[j] = value;
					}
					
					//tracks the number of modifications
					modificationCount++;
					return oldValue;
				}
			//current cell is null
			} else {
				//if no tombstones have been encountered
				if(j == -1) {
					usedBuckets++; keyCount++;
					keyTable[i] = key;
					valueTable[i] = value;
				//if we encountered a tombstone
				} else {
					keyTable[j] = key;
					valueTable[j] = value;
					keyCount++;
				}
				
				modificationCount++;
				return null;
				
			}
			//if the we encounter a different key in the spot
			x++;
			i = normalizeIndex(hash + P(x));
		} while(true);
	}
	
	//returns if a key exists 
	public boolean containsKey(K key) {
		return hasKey(key);
	}
	
	//method for finding a key
	private boolean hasKey(K key) {
		get(key);
		
		return containsFlag;
	}
	
	//Get the value associated with the input key
	//Returns null if the value is null and also returns null if the key does not exist
	public V get(K key) {
		if(key == null) return null;
		final int hash = normalizeIndex(key.hashCode());
		
		int i = hash, j = -1, x = 1;
		
		//starting at the original hash value we probe until we find the spot
		//where our key is or we hit a null element
		do {
			
			if(keyTable[i] == TOMBSTONE) {
				j = 1;
			} else if (keyTable[i] != null) {
				if(keyTable[i].equals(key)) {
					containsFlag = true;
					
					//if j != -1 we can put our obtained value into j, the first tombstone found
					if(j != -1) {
						keyTable[j] = keyTable[i];
						valueTable[j] = valueTable[i];
						keyTable[i] = null;
						valueTable[i] = null;
						return valueTable[j];
					} else {
						return valueTable[i];
					}
					
				} 
			} else {
				containsFlag = false;
				return null;
			}
			
			i = normalizeIndex(hash + P(x++));
			
		} while(true);
	}
	
	//removes a key from the map and returns the value
	//return null if the value is null and returns null if the key does not exist
	public V remove(K key) {
		if(key == null) throw new IllegalArgumentException("Null Key");
		
		final int hash = normalizeIndex(key.hashCode());
		int i = hash, x = 1;
		
		//start at hash value
		for(;; i = normalizeIndex(hash + P(x++))) {
			
			//ignore deleted cells
			if(keyTable[i] == TOMBSTONE) continue;
			
			//key wasnt found
			if(keyTable[i] == null) return null;
			
			//key is found
			if(keyTable[i].equals(key)) {
				
				keyTable[i] = TOMBSTONE;
				V oldValue = valueTable[i];
				valueTable[i] = null;
				keyCount--;
				modificationCount++;
				return oldValue;
				
			}
		}
	}
	
	//Space for list of keys and list of values but this is pretty self explanatory
	
	private void resizeTable() {
		
		capacity *= 2;
		
		//recompute the new threshold
		threshold = (int) (capacity * loadFactor);
		
		//allocate new memory for keys and values
		K[] oldKeyTable = (K[]) new Object[capacity];
		V[] oldValueTable = (V[]) new Object[capacity];
		
		//perform key table pointer swap
		//instance variable can be the new table
		K[] keyTableTemp = keyTable;
		keyTable = oldKeyTable;
		oldKeyTable = keyTableTemp;
		
		//perform a value table pointer swap
		//instance variable can be the new table
		V[] valueTableTemp = valueTable;
		valueTable = oldValueTable;
		oldValueTable = valueTableTemp;
		
		//reset key count and buckets used since we are going to 
		//re-insert all the keys
		keyCount = usedBuckets = 0;
		
		for(int i = 0; i < oldKeyTable.length; i++) {
			if(oldKeyTable[i] != null && oldKeyTable[i] != TOMBSTONE) {
				//re-insert the values
				//this will change keyCount and usedBuckets accordingly
				insert(oldKeyTable[i], oldValueTable[i]);
			}
			oldKeyTable[i] = null;
			oldValueTable[i] = null;
		}
		
		
	}
	
	
	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		for(int i = 0; i < capacity; i++) {
			if(keyTable[i] != null && keyTable[i] != TOMBSTONE) {
				sb.append(keyTable[i] + " => " + valueTable[i] + ", ");
			}
		}
		
		sb.append("}");
		return sb.toString();
	}
	
	

}
