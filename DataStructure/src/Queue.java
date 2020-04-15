import java.util.LinkedList;


//First In, Last out

public class Queue<T> {
	private LinkedList<T> list = new LinkedList();
	private T head;
	private T tail;
	
	public Queue() {}
	
	public Queue(T firstElement) {
		list.add(firstElement);
		this.head = firstElement;
		this.tail = firstElement;
	}
	
	public int size() {
		return this.list.size();
	}
	
	public boolean isEmpty() {
		return this.list.size() == 0;
	}
	
	public Object enqueue(T element) {
		this.list.add(element);
		this.tail = element;
		
		if(this.size() == 1) { this.head = element;}
		
		return element;
	}
	
	public Object dequeue(T element) {
		this.list.removeFirst();
		this.head = this.list.getFirst();
		return element;
		
	}
	
	
}
