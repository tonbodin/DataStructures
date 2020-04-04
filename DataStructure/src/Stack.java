import java.util.LinkedList;

public class Stack<T> {
	
	private LinkedList<T> list = new LinkedList();
	
	public Stack() {}
	
	public Stack(T obj) {
		this.list.add(obj);
	}
	
	public int size() {
		return this.list.size();
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	public Object push(T obj) {
		list.addFirst(obj);
		return obj;
		
	}
	
	public Object pop() {
		return list.removeFirst();
	}
	
	public Object peek() {
		return list.peekFirst();
	}
	
	public String toString() {
		return list.toString();
	}

}
