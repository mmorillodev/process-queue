public class Queue<T> {
	public Node<T> head;
	private int size;
	
	public Queue() {
		
	}
	
	public void add(T value) {
		if(isEmpty()) head = new Node<T>(value);
		else {
			getNode(size-1).nextNode = new Node<T>(value);
			getNode(size-1).nextNode.previousNode = getNode(size-1);
		}
		size++;
	}
	
	public T unQueue() {
		if(isEmpty()) return null;
		T v = head.value;
		head = head.nextNode;
		size--;
		return v;
	}
	
	
	public Object unQueueByPriority() throws Exception {
		if(isEmpty()) return null;
		if(!(head.value instanceof Prioriser)) 
			throw new Exception("Class must implement Prioriser");
		return null;
	}
	
	public T get(int i) {
		if(i == 0) return head.value;
		int c = 0;
		Node<T> current = head;
		while(current != null) {
			if(c == i) return current.value;
			c++;
			current = current.nextNode;
		}
		return null;
	}
	
	private Node<T> getNode(int i) {
		if(i == 0) return head;
		int c = 0;
		Node<T> current = head;
		while(current != null) {
			if(c == i) return current;
			c++;
			current = current.nextNode;
		}
		return null;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public String toString() {
		return null;
	}
}
class Node<T> {
	public T value;
	public Node<T> previousNode;
	public Node<T> nextNode;
	
	public Node(T value) {
		this.value = value;
	}
}
