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
		*/
		
		/*
		 * LinkedList<Integer> list = new LinkedList();
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
		 */
		
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
		
		PQueue<String> pq = new PQueue(4);
		
		pq.add("Hello");
		pq.add("Hi");
		pq.add("Bye");
		pq.add("Nice");
		
		System.out.println(pq.toString());
		
		
		
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