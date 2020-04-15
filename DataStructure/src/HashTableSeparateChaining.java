
import java.util.*;

//This is a HashTable Class that uses Seperate Chaining for Collision detection

//represented individual items you put into a Hash table
//should specify the type when initializing

class Entry<K,V> {
	
	//hash value for the key
	int hash;
	
	//the value of the key and value, key must be hashable
	K key; V value;
	
	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
		this.hash = key.hashCode(); //.hashCode() can be overrided by you to create a specific hash code for a specific program
	}
	
	//We are not overriding the Object equals method
	//No casting is required with this method
	//checking the equality of the keys, because values can vary
	public boolean equals(Entry <K,V> other) {
		if(hash != other.hash) return false;
		
		return key.equals(other.key);
	}
	
	//returns this entry as a String
	public String toString() {
		return key + " => " + value;
	}
	
}

public class HashTableSeparateChaining<K,V> implements Iterable<K> {
	
	//default number of entries
	private static final int DEFAULT_CAPACITY = 3;
	//default load factor to compute the threshold
	private static final double DEFAULT_LOAD_FACTOR = 0.75;
	
	private double maxLoadFactor;
	//threshold is the capacity multiplied by the maxLoadFactor
	//once the size surpasses the threshold we resize the capacity
	private int capacity, threshold, size = 0; 
	
	//the table itself, an array of Linked List
	private LinkedList<Entry<K,V>> [] table;
	
	//constructors
	public HashTableSeparateChaining () {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	public HashTableSeparateChaining (int capacity) {
		this(capacity, DEFAULT_LOAD_FACTOR);
	}
	
	//Designated Constructor
	//capacity must be greater than or equal to 0
	//maxLoadFactor must be greater than 0
	@SuppressWarnings("unchecked")
	public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
		this.maxLoadFactor = maxLoadFactor;
		this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
		this.threshold = (int) (this.capacity * maxLoadFactor);
		table = new LinkedList[this.capacity];
	}
	
	//Returns the number of entries in the linked list
	public int size() {
		return this.size;
	}
	
	//Checks if the Table is Empty
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	//Converts a hash value to an index. Essentially, this strips
	//the negative sign and places the hash value in the domain [0, capacity]
	private int normalizeIndex(int keyHash) {
		return (keyHash & 0x7FFFFFFF) % capacity;
	}
	
	//clears all contents of the hash Table
	public void clear() {
		Arrays.fill(table, null);
		size = 0;
	}
	
	//checks if Key is contained within the array
	public boolean containsKey(K key) { return hasKey(key); }
	
	public boolean hasKey(K key) {
		int bucketIndex = normalizeIndex(key.hashCode());
		return bucketSeekEntry(bucketIndex, key) != null;
	}
	
	//Insert, add, and put a value in the has table
	public V insert(K key, V value) {
		if(key == null) throw new IllegalArgumentException("null key");
		Entry<K,V> ent = new Entry<>(key, value);
		
		//the hashcode of the key from the entry
		int bucketIndex = normalizeIndex(ent.hash);
		
		return bucketInsertEntry(bucketIndex, ent);
	}

	public V put(K key, V value) { return insert(key, value);}
	public V add(K key, V value) { return insert(key, value);}
	
	//Gets a key value from the map and returns the value
	
	public V get(K key) {
		
		if(key == null) return null;
		
		int bucketIndex = normalizeIndex(key.hashCode());
		Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
		if(entry != null) return entry.value;
		return null;
		
	}
	
	//removes a key
	public V remove(K key) {
		
		if(key == null) return null;
		int bucketIndex = normalizeIndex(key.hashCode());
		
		return bucketRemoveEntry(bucketIndex, key);
	}
	
	//internal code 
	//internal code for removing an entry
	private V bucketRemoveEntry(int bucketIndex, K key) {
		
		Entry<K, V> ent = bucketSeekEntry(bucketIndex, key);
		
		if(ent != null) {
			LinkedList <Entry<K,V>> links = this.table[bucketIndex];
			links.remove(links.indexOf(ent));
			this.size--;
			return ent.value;
		} else return null;
	}
	
	//insert an entry into a linked list at the bucket Index, given the entry doesn't already exist
	//if the key is similar we update the entry value
	private V bucketInsertEntry(int bucketIndex, Entry<K, V> ent) {
		LinkedList<Entry<K,V>> list = table[bucketIndex];
		if(list == null) table[bucketIndex] = list = new LinkedList<>();
		
		Entry<K,V> existentEntry = bucketSeekEntry(bucketIndex, ent.key);
		
		if(existentEntry == null) {
			list.addLast(ent);
			if( ++this.size > threshold) resizeTable();
			return null; //null indicates that there was no previous entry
		} else {
			V oldVal = existentEntry.value;
			existentEntry.value = ent.value;
			return oldVal; //return the value replaced
		}
	}
	
	//find and return an entry within the bucket of the hashmap using the key
	private Entry<K, V> bucketSeekEntry(int bucketIndex, K key) {
		if(key == null) return null;
		
		LinkedList<Entry<K,V>> bucket = table[bucketIndex];
		if(bucket == null) {return null;}
		for(Entry<K,V> ent : bucket) {
			if(ent.key == key) {
				return ent;
			}
		}
		return null;
	}
	
	//resizes the internal table holding buckets of entries
	private void resizeTable() {
		capacity *= 2;
		threshold = (int) (capacity * maxLoadFactor);
		
		//create a new table
		LinkedList<Entry<K,V>> [] newTable = new LinkedList[capacity];
		
		//iterate and insert existing entries into the new Table
		for(int i = 0; i < table.length; i++) {
			if(table[i] != null) {
				for(Entry<K,V> entry : table[i]) {
					int newIndex = normalizeIndex(entry.hash);
					LinkedList<Entry<K,V>> bucket = newTable[newIndex];
					if(bucket == null) newTable[newIndex] = bucket = new LinkedList<>();
					bucket.addLast(entry);
				}
				//avoid memory leaks
				table[i].clear();
				table[i] = null;
			}
		}
		
		//set the instance variable table to the new Table
		this.table = newTable;
	}
	
	//Returns the list of keys found within the Hash Table
	public List<K> keys() {
		List<K> keys = new ArrayList<>(size());
		
		for(LinkedList<Entry<K,V>> bucket : table) {
			if(bucket != null) {
				for(Entry<K,V> entry: bucket) {
					keys.add(entry.key);
				}
			}
		}
		return keys;
	}
	
	//Returns the list of keys found within the Hash Table
		public List<V> values() {
			List<V> values = new ArrayList<>(size());
			
			for(LinkedList<Entry<K,V>> bucket : table) {
				if(bucket != null) {
					for(Entry<K,V> entry: bucket) {
						values.add(entry.value);
					}
				}
			}
			return values;
		}

	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	//convert to string
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		for(int i = 0; i < capacity; i++) {
			if(table[i] == null) continue;
			
			for(Entry<K,V> entry : table[i]) {
				sb.append(entry + ", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}
	
}
