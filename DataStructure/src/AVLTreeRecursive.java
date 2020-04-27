import java.util.Iterator;

/*
 * This is a alteration of a binary tree that maintains balance in a tree
 * We must keep track of a node's balance factor, 
 * which is the height of the right subtree subtracted by the height of the left subtree
 * We also must update the balance factor accordingly after inserts and rotations, 
 * maintaining a BF of -1, 0, or 1.
 * If a tree is not balanced, we must perform rotations to make it so that the tree is balanced. 
 */
public class AVLTreeRecursive <T extends Comparable<T>> implements Iterable<T> {
	
	//We must create a private Node Class for the elements within the Tree
	class Node {
		//bf stands for the balance factor
		int bf;
		
		//The value/data contained within the node
		T value;
		
		//the height of the tree, 
		//represented by the number of edges down to the furthest edge
		int height;
		
		//the left and right children of this node
		Node left, right;
		
		//constructor
		public Node(T value) {
			this.value = value;
		}
		
		public Node getLeft() {
			return left;
		}
		
		public Node getRight() {
			return right;
		}
		
		public String getText() {
			return String.valueOf(value);
		}
	}
	
	//The root node of the tree
	Node root;
	
	//number of nodes contained within the tree
	private int nodeCount = 0;
	
	//The height of a rooted tree is the number of edges to the furthest leaf node
	public int height() {
		if(root == null) return 0;
		return root.height;
	}
	
	//returns the number of nodes in the tree
	public int size() {
		return nodeCount;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	//returns true/false depending on if node is in the tree
	public boolean contains(T value) {
		return contains(root, value);
	}
	
	//recursive contains helper method
	private boolean contains(Node node, T value) {
		if(node == null) return false;
		
		//compare current value to the left and right nodes
		int cmp = value.compareTo(node.value);
		
		//if value is less than the node
		//go into the left subtree
		if(cmp < 0) {
			return contains(node.left, value);
		}
		
		if(cmp > 0) {
			return contains(node.right, value);
		}
		
		//found value in tree
		return true;
	}
	
	//Insert/add a value to the AVL tree. The value must not be null. 
	public boolean insert(T value) {
		if(value == null) return false;
		if(!contains(root, value)) {
			root = insert(root, value);
			nodeCount++;
			return true;
		}
		return false;
	}
	
	//private insert method
	private Node insert(Node node, T value) {
		
		//Base Case
		if(node == null) return new Node(value);
		
		//compare current value to the value in the node
		int cmp = value.compareTo(node.value);
		
		//insert node into the left subtree
		if(cmp < 0) {
			node.left = insert(node.left, value);
		}
		
		//insert node into the right subtree
		if(cmp > 0) {
			node.right = insert(node.right, value);
		}
		
		//update the balance factor and height values for the node
		update(node);
		
		//re-balance the tree
		return balance(node);
	}
	
	//Update a node's balance factor and height
	private void update(Node node) {
		
		int leftNodeHeight = (node.left == null) ? -1 : node.left.height;
		int rightNodeHeight = (node.right == null) ? -1 : node.right.height;
		
		//update the height of the node
		//if the node is a lone node, the max will be -1,
		//resulting in a balance factor of 0.
		node.height = 1 + Math.max(leftNodeHeight, rightNodeHeight);
		
		//update balance factor
		node.bf = rightNodeHeight - leftNodeHeight;
	}
	
	//rebalance a node if it's balance factor is +2 or -2
	//left-left case in which we only do a right rotation
	//left-right case in which we left rotate the middle node to obtain a left-left
	//right-right case in which we only do a left rotation
	//right-left case in which we right rotate the middle nod to obtain a right-right
	private Node balance(Node node) {
		
		//left heavy subtree
		if(node.bf == -2) {
			
			//left-left case
			if(node.left.bf <= 0) {
				return leftLeftCase(node);
				
				//left-right case
			} else {
				return leftRightCase(node);
			}
		//right heavy subtree
		} else if(node.bf == +2) {
			//right-right case
			if(node.left.bf >= 0) {
				return rightRightCase(node);
				
				//right-left case
			} else {
				return rightLeftCase(node);
			}
		}
		
		return node;
		
	}
	
	//left-left case
	private Node leftLeftCase(Node node) {
		return rightRotation(node);
	}
	
	//left-right case
	private Node leftRightCase(Node node) {
		node.left = leftRotation(node.left);
		return leftLeftCase(node);
	}
	
	//right-right case
	private Node rightRightCase(Node node) {
		return leftRotation(node);
	}
		
	//right-left case
	private Node rightLeftCase(Node node) {
		node.right = rightRotation(node.right);
		return rightRightCase(node);
	}
	
	//we set the middle node as the root for leftRotation and rightRotation
	//if the middle node has a left leaf we set it to the right leaf of the current root
	private Node leftRotation(Node node) {
		Node newParent = node.right;
		node.right = newParent.left;
		newParent.left = node;
		update(node);
		update(newParent);
		//new root 
		return newParent;
	}
	
	//opposite for rightRotation
	private Node rightRotation(Node node) {
		Node newParent = node.left;
		node.left = newParent.right;
		newParent.right = node;
		update(node);
		update(newParent);
		//new root 
		return newParent;
	}
	
	//remove a value from this binary tree if it exists
	public boolean remove(T elem) {
		if(elem == null) return false;
		
		if(contains(root, elem)) {
			root = remove(root, elem);
			nodeCount--;
			return true;
		}
		
		return false;
	}
	
	//removes a value (private)
	private Node remove(Node node, T elem) {
		
		if(node == null) return null;
		
		int cmp = elem.compareTo(node.value);
		
		//dig into the left subtree if the value we're looking for is smaller than the current value
		if(cmp < 0) {
			node.left = remove(node.left, elem);
		}
		
		//dig into the right subtree if the value we're looking for is greater than the current value
		else if(cmp > 0) {
			node.right = remove(node.right, elem);
		}
		//Found the node
		else {
			
			//this is the case with only a right-subtree or no subtrees at all
			//swap the node we wish to remove with it's right child.
			if (node.left == null) {
				return node.right;
				
			//replace the root with it's left node
			} else if (node.right == null) {
				return node.left;
				
			//Both leafs are filled. If the left leaf is larger than the right leaf, remove from the left leaf
			//and vice versa
			//we either replace the root with the largest element in the left tree or the smallest element in the right tree
			} else {
				if(node.left.height > node.right.height) {
					//find the max value in the left sub-tree
					T successorValue = findMax(node.left);
					node.value = successorValue;
					
					//remove the successor value from the tree
					node.left = remove(node.left, successorValue);
					
				//opposite condition
				} else {
					//find the min value in the left sub-tree
					T successorValue = findMin(node.right);
					node.value = successorValue;
					
					//remove the successor value from the tree
					node.right = remove(node.right, successorValue);
					
				}
			}
			
		}
		
		update(node);
		
		return balance(node); 

	}
	
	private T findMin(Node node) {
		while(node.left != null) {
			node = node.left;
		}
		
		return node.value;
	}
	
	private T findMax(Node node) {
		while(node.right != null) {
			node = node.right;
		}
		
		return node.value;
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
