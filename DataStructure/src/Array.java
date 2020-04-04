import java.util.Iterator;

public class Array <T> implements Iterator<T> {
	
	private T [] arr;
	private int len = 0; //perceived length of array
	private int cap = 0; //size of the actual array
	
	@SuppressWarnings("unchecked")
	public Array(int cap) { 
		this.cap = cap; 
		this.arr = (T[]) new Object[this.cap];
	}
	
	public Array() {this(2);}
	
	public int size() {
		return this.len;
	}
	
	public T get(int index) {
		return this.arr[index];
	}
	
	public int indexOf(T val ) {
		for(int i = 0; i < this.len; i++) {
			if(this.arr[i].equals(val)) {
				return i;
			}
		}
		
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public T add(T val, int index) {
		if(this.len+1 > this.cap) {
			this.cap *= 2;
		}
		
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[this.len+1];
		
		for(int i = 0; i < temp.length; i++) {
			
			if(i < index) {
				temp[i] = this.arr[i];
			} else if(i == index) {
				temp[i] = val;
			} else {
				temp[i] = this.arr[i-1];
			}
			
		}
		
		this.arr = (T[]) new Object[this.cap];
		
		for(int i = 0; i < temp.length; i++) {
			this.arr[i] = temp[i];
		}
		this.len++;
		return val;
		
	}
	
	public T add(T val) {
		this.add(val, this.len);
		
		return val;
	}
	
	@SuppressWarnings("unchecked")
	public T remove(int index) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[this.len-1];
		T val = this.arr[index];
		
		for(int i = 0; i < temp.length; i++) {
			if(i < index) {
				temp[i] = this.arr[i];
			} else if (i >= index) {
				temp[i] = this.arr[i+1];
			}
		}
		
		this.arr = (T[]) new Object[this.cap];
	
		for(int i = 0; i < temp.length; i++) {
			this.arr[i] = temp[i];
		}
		
		this.len--;
		return val;
	}
	
	public String toString() {
		String val = "";
		for(int i = 0; i < this.len; i++) {
			val += this.arr[i] + " ";
		}
		
		return val;
	}
	

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		return null;
	}

}
