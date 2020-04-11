
//UnionFind groups elements into disjointed groups. Each element points towards one node, and in the 
//beginning, points to itself.

public class UnionFind {
	
	//the number of elements in the union find 
	private int size;
	
	//used to track the sizes of each of the components
	private int[] sz; 
	
	//id[i] points to the parent of i, if id[i]= i then i is the root node, this is like the bijection
	private int[] id;
	
	//tracks the number of components in the union find
	private int numComponents;
	
	public UnionFind(int size) {
		if(size <= 0) {
			throw new IllegalArgumentException("Size <= 0 is not allowed");
		}
		
		this.size = numComponents = size;
		sz = new int[size];
		id = new int[size];
		
		for(int i = 0; i < size; i++) {
			id[i] = i; //root node in the beginning
			sz[i] = 1; //Each component is originally of size one
		}
	}
	
	/*Find out which component integer p (index) belongs to
	 * This function takes up amortized time (not quite constant time) 
	 */
	
	public int find(int p) {
		
		//Find the root of the component
		int root = p; //set the root equal to p
		
		while(root != id[root]) { 
			root = id[root]; //set root to its parent
		}
		
		//compression of path leading back to the root
		//this is what gives us amortized constant time (better time complexity)
		
		while(p != root) {
			int next = id[p];
			id[p] = root;
			p = next; 
		}
		
		return root;
		
	}
	
	//Finds out if p and q are of the same component
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public int componentSize(int p) {
		return sz[find(p)];
	}
	
	public int size() {
		return this.size;
	}
	
	public int components() {
		return numComponents;
	}
	
	//unify the components/sets containing elements 'p' and 'q'
	public void unify(int p, int q) {
		int root1 = find(p);
		int root2 = find(q);
		
		if(root1 == root2) {
			return; // already in the same component
		}
		
		//Merge smaller group into larger group
		if(sz[root1] < sz[root2]) {
			sz[root2] += sz[root1];
			id[root1] = root2;
		} else {
			sz[root1] += sz[root2];
			id[root2] = root1;
		}
		
		numComponents--;
	}
	
	
	
}
