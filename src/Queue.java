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
	
	@SuppressWarnings("unchecked")
	public T unQueueByPriority() throws Exception {
		if(isEmpty()) return null;
		if(!(head.value instanceof Prioriser))
			throw new Exception("Class must implement Prioriser");
		
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
	
	public boolean remove(T object) {
		Node<T> current = head;
		int i = 0;
		
		while(current != null) {
			if(current.value == object) return remove(i);
			current = current.nextNode;
			i++;
		}
		return false;
	}
	
	public boolean remove(int index) {
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
	
	public Node<T> getNode(int i) {
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
			str.append(current.value.toString() + (current.nextNode != null ? ", " : ""));
			current = current.nextNode;
		}
		return str.append("]").toString();
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
