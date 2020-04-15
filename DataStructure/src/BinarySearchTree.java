

public class BinarySearchTree <T extends Comparable<T>> {
	
	//keep track of the number of nodes in the tree
	private int nodeCount = 0;
	
	//The BST is a rooted tree, therefore we must maintain a proper reference to the root
	private Node root = null;
	
	//Internal Node containing node references
	//and the actual node data
	
	private class Node{
		T data; 
		Node left, right;
		
		public Node (Node left, Node right, T elem) {
			this.data = elem;
			this.left = left;
			this.right = right; 
		}
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public int size() {
		return nodeCount;
	}
	
	//theres also a private method used to conduct the recursion
	public boolean add(T elem) {
		if(contains(elem)) {
			return false;
		} else {
			root = add(root, elem);
			nodeCount++;
			return true;
		}
	}
	
	//private method to handle recursion
	public Node add(Node node, T elem) {
		//Base Case
		if(node == null) {
			node = new Node(null, null, elem);
		} else {
			if(elem.compareTo(node.data) < 0) {
				node.left = add(node.left, elem);
			} else {
				node.right = add(node.right, elem);
			}
		}
		
		return node;
	}
	
	public boolean remove(T elem) {
		if(contains(elem)) {
			root = remove(root, elem);
			nodeCount--;
			return true;
		} else {
			return false;
		}
	}
	
	private Node remove(Node node, T elem) {
		if(node == null) {
			return null;
		}
		
		int cmp = elem.compareTo(node.data);
		
		if(cmp < 0) {
			node.left = remove(node.left, elem);
		} else if( cmp > 0) {
			node.right = remove(node.right, elem);
		} else {
			if(node.left == null) {
				Node rightChild = node.right;
				
				node.data = null;
				node = null;
				return rightChild;
			} else if (node.right == null) {
				Node leftChild = node.left;
				node.data = null;
				node = null;
				return leftChild;
				
				
				
				//the successor can either be the largest value in the left subtree or the smallest value in the right subtree
				//in this case, we are finding the smallest element in the right subtree
			} else {
				//find the left most node in the right subtree
				Node tmp = digLeft(node.right);
				
				//swap the data
				node.data = tmp.data;
				
				//go into the right subtree and just remove the leftmost node we found
				remove(node.right, tmp.data);
			}
		}
		
		return node;
	}
	
	public Node digLeft(Node node) {
		if(node.left == null) {
			return node;
		}
		
		return digLeft(node.left);
	}

	public boolean contains(T elem) {
		return contains(root, elem);
	}
	
	private boolean contains(Node node, T elem) {
		if(node == null) {
			return false;
		}
		
		int cmp = elem.compareTo(node.data);
		
		if(cmp < 0) {
			return contains(node.left, elem);
		} else if (cmp > 0) {
			return contains(node.right, elem);
		} else {
			return true;
		}
	}

}
