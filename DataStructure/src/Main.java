import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		/*
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter bracket sequence: ");
		
		String seq = sc.next();
		
		Stack<String> st = new Stack();	
		boolean sym = true;
		
		for(int i = 0; i < seq.length(); i++) {
			
			String bracket = Character.toString(seq.charAt(i));
			
			if(isLeftBracket(bracket)) {
				st.push(bracket);
			} else {
				if(!(getReverse(st.peek().toString()).equals(bracket))){
					sym = false;
					break;
				} else {
					st.pop();
				}
			}
			
		}
		
		System.out.println(sym && st.isEmpty());
		
		
		
		LinkedListClass<Integer> list = new LinkedListClass();
		System.out.println("Added: " + list.addLast(2));
		System.out.println("List: " + list.toString());
		System.out.println("List Size " + list.size());
		System.out.println("Added: " + list.addLast(3));
		System.out.println("List Size " + list.size());
		System.out.println("List: " + list.toString());
		System.out.println("Added: " + list.addLast(4));
		System.out.println("List Size " + list.size());
		System.out.println("List: " + list.toString());
		System.out.println("Added: " + list.add(10, 2));
		System.out.println("List Size " + list.size());
		System.out.println("List: " + list.toString());
		System.out.println("Added: " + list.add(9, 0));
		System.out.println("List Size " + list.size());
		System.out.println("List: " + list.toString());
		System.out.println("Removed: " + list.remove(0));
		System.out.println("List Size " + list.size());
		System.out.println("List: " + list.toString());
		
		
		/*
		PriorityQueue<Integer> pr = new PriorityQueue();
		
		pr.add(1);
		pr.add(10);
		pr.add(12);
		pr.add(15);
		pr.add(17);
		pr.add(16);
		pr.add(3);
		
		System.out.println(pr.toString());
		System.out.println(pr.poll());
		System.out.println(pr.toString());
		System.out.println(pr.poll());
		*/
		/*
		PQueue<String> pq = new PQueue(4);
		
		pq.add("Hello");
		pq.add("Hi");
		pq.add("Bye");
		pq.add("Nice");
		
		System.out.println(pq.toString());
		
		
		
		//Implementation of Union Find using a HashMap
		
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		
		
		//adding arbitrary values to the hashmap
		hm.put(0, "A");
		hm.put(1, "C");
		hm.put(2, "D");
		hm.put(3, "F");
		hm.put(4, "B");
		hm.put(5, "E");
		
		UnionFind uf = new UnionFind(6);
		
		System.out.println(hm.get(uf.find(0)));
		uf.unify(2, 3);
		System.out.println(hm.get(uf.find(2)));
		System.out.println(hm.get(uf.find(3)));
		
		
		//Binary Search Tree Test
		BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
		System.out.println(bst.add(1));
		System.out.println(bst.add(-1));
		System.out.println(bst.add(0));
		System.out.println(bst.add(2));
		System.out.println(bst.add(3));
		System.out.println(bst.remove(2)); 
		System.out.println(bst.size());
		
		*/
		
		/*
		
		//Separate Chaining Test
		HashTableSeparateChaining<Integer, String> ht = new HashTableSeparateChaining<>();
		ht.add(2, "Hello");
		ht.add(3, "Now");
		ht.add(4, "LOL");
		ht.add(8, "Yee");
		ht.remove(8);
		System.out.println(ht.toString());
		*/
		/*
		//hash table quadratic probing test
		HashTableQuadraticProbing<Integer, String> qp = new HashTableQuadraticProbing<>();
		qp.add(2, "Hello");
		qp.add(3, "Now");
		qp.add(4, "LOL");
		qp.add(8, "Yee");
		qp.remove(8);
		
		System.out.println(qp.toString());
		*/
		
		//Testing Fenwick Tree
		int[] arr = new int[] {3,2,-1, 6, 5, 4, -3,3, 7,2,3};
		FenwickTree ft = new FenwickTree(arr);
		
		System.out.println(ft.toString());
		ft.set(6, 2);
		System.out.println(ft.toString());
	}
	
	public static boolean isLeftBracket(String bracket) {
		
		if(bracket.equals("[") || bracket.equals("{") || bracket.equals("(")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getReverse(String bracket) {
		switch(bracket) {
		case "{": return "}"; 
		case "}": return "{"; 
		case "[": return "]"; 
		case "]": return "["; 
		case "(": return ")"; 
		case ")": return "("; 
		}
		
		return "";
	}

}
