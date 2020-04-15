import java.util.Iterator;

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
	
	
	
	
	
	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
