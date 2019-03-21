import aux_interfaces.MyConsumer;
import aux_interfaces.Prioriser;

public class Queue<T> {
	private Node<T> head;
	private Node<T> last;
	private int size;
	
	public Queue() {
		
	}
	
	public void add(T value) {
		Node<T> newNode = new Node<T>(value);
		if(isEmpty()) {
			head = newNode;
			last = newNode;
		}
		else {
			last.nextNode = newNode;
			last = newNode;
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
	
	@SuppressWarnings("unchecked")
	public T unQueueByPriority() {
		if(isEmpty()) return null;
		if(!(head.value instanceof Prioriser))
			throw new ClassCastException("Class must implement Prioriser");
		
		Prioriser maxPriority = (Prioriser) head.value;
		Prioriser next;
		for(int i = 1; i < size; i++) {
			next = (Prioriser) get(i); 
			if(next.getPriority() > maxPriority.getPriority()) {
				maxPriority = next;
			}
		}
		
		T statement = (T) maxPriority;
		remove(statement);
		return statement;
	}
	
	private T get(int i) {
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
	
	private boolean remove(T object) {
		Node<T> current = head;
		int i = 0;
		
		while(current != null) {
			if(current.value == object) return remove(i);
			current = current.nextNode;
			i++;
		}
		return false;
	}
	
	private boolean remove(int index) {
		boolean success = false;
		Node<T> removed = getNode(index);
		if(isEmpty());
		else if(index >= size);
		else if(index == 0) {
			head = head.nextNode;
			success = true;
		}
		else if(index == size-1) {
			removed.previousNode.nextNode = null;
			success = true;
		}
		else {
			removed.nextNode.previousNode = removed.previousNode;
			removed.previousNode.nextNode = removed.nextNode;
			success = true;
		}
		
		if(success) size--;
		return success;
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
	
	public void forEach(MyConsumer<T> consumer) {
		for(int i = 0; i < size; i++) {
			consumer.action(get(i));
		}
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		
		if(isEmpty()) return str.append("]").toString();
		
		Node<T> current = head;
		while(current != null) {
			str.append(current.value.toString() + (current.nextNode != null ? "; " : ""));
			current = current.nextNode;
		}
		return str.append("]").toString();
	}
}
class Node<T> {
	T value;
	Node<T> previousNode;
	Node<T> nextNode;
	
	public Node(T value) {
		this.value = value;
	}
}
