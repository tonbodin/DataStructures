import java.util.*;

public class PQueue <T extends Comparable<T>> { 	
	
	//the number of elements currently inside the heap
	private int heapSize = 0; 
	
	//internal capacity of heap
	private int heapCapacity = 0; 
	
	//hold the elements of our heap
	private List<T> heap = null;
	
	
	//map each element to an index in a tree to prevent the need for searching
	private Map<T, TreeSet<Integer>> map = new HashMap<>();
	
	
	public PQueue() {
		this(1);
	}
	
	public PQueue(int size) {
		heap = new ArrayList<>(size);
		
	}
	
	//creating a PQueue in Linear Time using Heapify
	
	public PQueue(T[] elems) {
		
		heapSize = heapCapacity = elems.length;
		heap = new ArrayList<T> (heapCapacity);
		
		//placing the elements within the heap
		for(int i = 0; i < elems.length; i++) {
			heap.add(elems[i]);
			mapAdd(elems[i], i);
		}
		
		//heapify process
		for(int i = Math.max(0, (heapSize/2)-1); i >= 0; i--) {
			sink(i);
		}
		
	}
	
	public PQueue(Collection<T> elems) {
		this(elems.size());
		for( T elem : elems) {add(elem);}
	}
	
	public boolean isEmpty() {
		return heapSize == 0;
	}
	
	public void clear() {
		for(int i = 0; i < heapCapacity; i++) {
			heap.set(i, null);
		}
		heapSize = 0;
		map.clear();
	}
	
	public int size() {
		return heapSize;
	}
	
	public T peek() {
		if(isEmpty()) {return null;}
		
		return heap.get(0);
	}
	
	public T poll() {
		return removeAt(0);
	}
	
	public boolean contains(T elem) {
		if(elem == null) {return false;}
		
		return map.containsKey(elem);
	}
	
	//Key Method Add
	public void add(T elem) {
		if (elem == null) {
			throw new IllegalArgumentException();
		}
		
		if(heapSize < heapCapacity) {
			heap.set(heapSize, elem);
		} else {
			heap.add(elem);
			heapCapacity++;
		}
		
		mapAdd(elem, heapSize);
		
		swim(heapSize);
		heapSize++;
	}
	
	private boolean less(int i, int j) {
		
		T node1 = heap.get(i);
		T node2 = heap.get(j);
		return node1.compareTo(node2) <= 0;
	}
	
	private void swim(int k) {
		
		int parent = (k-1)/2;
		
		while(k > 0 && less(k, parent)) {
			swap(parent, k);
			k = parent;
			
			parent = (k-1)/2;
		}
		
	}
	
	private void sink(int k) {
		
		while(true) {
			int left = 2*k;
			int right = (2*k)+1;
			int smallest = left;
			
			if(right < heapSize && less(right,left)) {
				smallest = right;
			}
			
			if(left >= heapSize || less(k, smallest)) {
				break;
			}
			
			swap(smallest, k);
			k = smallest;
		}
		
	}
	
	//swapping the elements in the heap
	private void swap(int i, int j) {
		T elem1 = heap.get(i);
		T elem2 = heap.get(j);
		
		heap.set(i, elem2);
		heap.set(j, elem1);
		
		//must classify which index to swap
		mapSwap(elem1, elem2, i, j);
	}
	
	public boolean remove(T element) {
		if(element == null) {return false;}
		
		Integer index = mapGet(element);
		if (index != null) {removeAt(index);}
		return index != null;
	}
	
	private T removeAt(int i) {
		if(isEmpty()) {return null;}
		
		heapSize--;
		T removed_data = heap.get(i);
		swap(i, heapSize);
		
		heap.set(heapSize, null);
		mapRemove(removed_data, heapSize);
		
		
		if(i == heapSize) return removed_data;
		
		T elem = heap.get(i);
		
		sink(i);
		
		if(heap.get(i).equals(elem)) {
			swim(i);
		}
			
		return removed_data;
		
		
	}
	
	//for testing purposes
	
	public boolean isMinHeap(int k) {
		
		if(k >= heapSize) return true;
		
		int left = 2*k+1;
		int right = 2*k + 2;
		
		if(left < heapSize && !less(k, left)) return false;
		if(right < heapSize && !less(k, right)) return false;
		
		return isMinHeap(left) && isMinHeap(right);
	}
	
	private void mapAdd(T value, int index) {
		TreeSet<Integer> set = map.get(value);
		
		if(set == null) {
			set = new TreeSet<>();
			set.add(index);
			map.put(value, set);
		} else set.add(index);
	}
	
	private void mapRemove(T value, int index) {
		TreeSet<Integer> set = map.get(value);
		set.remove(index);
		if(set.size() == 0) map.remove(value);
	}
	
	private Integer mapGet(T value) {
		TreeSet<Integer> set = map.get(value);
		if(set != null) return set.last();
		return null;
	}
	
	private void mapSwap(T val1, T val2, int val1index, int val2index) {
		TreeSet<Integer> set1 = map.get(val1);
		TreeSet<Integer> set2 = map.get(val2);
		
		set1.remove(val1index);
		set2.remove(val2index);
		
		set1.add(val2index);
		set2.add(val1index);
		
	}
	
	public String toString() {
		return heap.toString();
	}
	
	
	
}
