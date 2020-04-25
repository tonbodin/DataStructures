import java.util.Arrays;

//store the sum of an array in a Fenwick Tree
//see fenwick tree folder in onenote


public class FenwickTree {
	
	//this array contains the fenwick tree ranges
	private long[] tree;
	
	//create an empty Fenwick Tree
	public FenwickTree(int sz) {
		//one greater than the size because tree[0] = 0
		//Fenwick Tree Array is one-based
		tree = new long[sz + 1];
	}
	
	//Transferring elements from a 'values' array into a fenwick tree
	//values[0] is not used, equal to 0. 
	public FenwickTree(long[] values) {
		
		//values can't be null
		if(values == null) {
			throw new IllegalArgumentException("Null Values");
		}
		
		this.tree = values.clone();
		
		//perform the construct function
		//enter new values into the tree
		for(int i = 1; i < this.tree.length; i++) {
			int j = i + lsb(i);
			if(j < this.tree.length) {
				this.tree[j] += this.tree[i];
			}
		}
		
	}
	
	//For testing purposes
	public FenwickTree(int[] values) {
		
		this.tree = new long[values.length + 1];
		this.tree[0] = 0;
		for(int i = 0; i < values.length; i++) {
			this.tree[i+1] = values[i];
		}
		
		//perform the construct function
		//enter new values into the tree
		for(int i = 1; i < this.tree.length; i++) {
			int j = i + lsb(i);
			if(j < this.tree.length) {
				this.tree[j] += this.tree[i];
			}
		}
		
		
	}
	
	//find the least significant bit of an integer
	//lsb(64) = lsb(01000000) = 1000000 = 64
	//lsb(7) = lsb(00000111) = 00000001 = 1
	
	private int lsb(int i) {
		//isolates the lowest one bit value
		return i & -i;
		
		
		//you can also use java's built in method
		//return Integer.lowestOneBit(i)
	}
	
	//Computes the prefix sum from [1, i], one based
	public long prefixSum(int i) {
		
		if(i <= 0 || i > this.tree.length) {
			return -1;
		}
		
		long sum = 0L;
		
		while(i != 0) {
			sum = sum + this.tree[i];
			//go down one least significant bit for i until you reach 0
			i -= lsb(i);
		}
		
		return sum;
		
	}
	
	//Returns the sum of the interval [i, j], one based
	public long sum(int i, int j) {
		if(j < i) throw new IllegalArgumentException("Range Error");
		return prefixSum(j) - prefixSum(i-1);
	}
	
	//set 'i' to 'k'
	//we only need to update the values at i, 
	//and the succession of least significant bits above i, until we reach the size
	public void set(int i, long k) {
		
		if(i <= 0 || i > this.tree.length) {
			throw new IndexOutOfBoundsException("");
		}
		
		long value = sum(i,i);
		
		while(i < this.tree.length) {
			
			this.tree[i] += (k-value);
			i = i + lsb(i);
	
		}
		
	}
	
	//toString
	public String toString() {
		return Arrays.toString(tree);
	}
	
	
	

}
