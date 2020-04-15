import java.util.Iterator;

public class LinkedListClass <T> implements Iterable<T> {
	
	 private int size = 0;
	 private Node<T> head = null;
	 private Node<T> tail = null;
	 
	 private class Node<T> {
			
			private T value = null;
			private Node<T> next = null;
			private Node<T> prev = null;
			
			public Node(T value, Node<T> prev, Node<T> next) {
				this.value = value; 
				this.next = next;
				this.prev = prev;
			}
			
			public String toString() {
				return value.toString();
			}

	 }
	 
	 public int indexOf(Object obj) {
		 
		 int counter = 0;
		 
		 
		 for(Node temp = this.head; temp != null; temp = temp.next ) {
			 if(temp.value.equals(obj)) {
				 return counter;
			 }
			 
			 counter++;
		 }
		 
		 return -1;
	 }
	 
	 
	 
	 public int size() {
		 return this.size;
	 }
	 
	 public String toString() {
		 String val = "";
		 
		 if(this.size == 0 || this.size == 1) {
			return val += this.head.value;
		 }
		
		 for(Node temp = this.head; temp != null; temp = temp.next) {
			 val+= temp.value + " ";
		 }
		 return val;
	 }
	
	 
	 public Object addLast(Object obj) {
		 
		 if(this.size == 0) {
			 this.head = new Node(obj, null, null);
			 this.tail = this.head;
			 this.size++;
			 return obj;
		 }
		 
		 Node addedElement = new Node(obj, this.tail, null);
		 this.tail.next = addedElement;
		 this.tail = addedElement;
		 this.size++;
		 return this.tail.value;
		 
	 }
	 
	 public Object addFirst(Object obj) {
		 if(this.size == 0) {
			 return addLast(obj);
		 }
		 
		 Node temp = new Node(obj, null, this.head);
		 this.head.prev = temp;
		 this.head = temp;
		 this.size++;
		 return this.head.value;
	 }
	 
	 public Object add(Object obj, int index) {
		 
		 
		 if(index == 0) {
			 return addFirst(obj);
		 } else if (index == this.size) {
			 return addLast(obj);
		 }
		 
		 Node temp = this.head;
		 
		 for(int i = 0; i < index-1; i++) {
			 temp = temp.next;
		 }
		 
		 Node element = new Node(obj, temp, temp.next);
		 temp.next.prev = element;
		 temp.next = element;
		 this.size++;
		 return obj;
	 }
	 
	 
	 public Object remove(int index) {
		 
		 if(index == 0) {
			 Object temp = this.head.value;
			 
			 this.head = this.head.next;
			 this.size--;
			 if(this.size == 0) {
				 this.tail = null;
			 } else {
				 this.head.prev = null;
			 }
			 
			 
			 return temp;
			 
		 }
		 
		 Node trav = this.head;
		 
		 
		 if(index == this.size - 1) {
			 Object temp = this.tail.value;
			 this.tail = this.tail.prev;
			 this.tail.next = null;
			 this.size--;
			 return temp;
			 
		 }
		 
		 
		 for(int i = 0; i < index; i++ ) {
			 trav = trav.next;
		 }
		 
		 Object temp = trav.value;
		 trav.next.prev = trav.prev;
		 trav.prev.next = trav.next;
		 
		 trav.value = null;
		 trav.next = trav.prev = trav = null;
		 this.size--;
		 return temp;
		 
		 
		 
		 
	 }



	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}



	public void clear() {
		
		while(this.head != null) {
			this.remove(0);
		}
		
	}
	 

	

}
