package br.com.aps_so.lists;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.MyConsumer;
import br.com.aps_so.interfaces.MyPredicate;
import br.com.aps_so.interfaces.Prioriser;
import br.com.aps_so.exceptions.ImplementationNotFoundException;
import br.com.aps_so.lists.Node;

public class Queue<T> {
	private Node<T> head;
	private Node<T> last;
	private int size;
	
	public Queue() {
		
	}
	
	public Queue(T[] arr) {
		for(int i = 0; i < arr.length; i++) {
			add(arr[i]);
		}
	}
	
	public void add(T value) {
		Node<T> newNode = new Node<T>(value);
		if(isEmpty()) {
			head = last = newNode;
		}
		else {
			last.nextNode = newNode;
			newNode.previousNode = last;
			last = newNode;
		}
		size++;
	}
	
	public boolean addIfNotExist(T value) {
		if(!contains(value)) {
			add(value);
			return true;
		}
		return false;
	}
	
	public T unQueue() {
		if(isEmpty()) return null;
		T v = head.data;
		head = head.nextNode;
		size--;
		return v;
	}
	
	@SuppressWarnings("unchecked")
	public T unQueueByPriority() {
		if(isEmpty()) return null;
		if(!(head.data instanceof Prioriser))
			throw new ImplementationNotFoundException(head.data.getClass() + " must implement Prioriser");
		
		Prioriser maxPriority = (Prioriser) head.data;
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
		if(i == 0) return head.data;
		int c = 0;
				
		for(Node<T> current = head; current != null; current = current.nextNode, c++) {	
			if(c == i) return current.data;
		}
		
		return null;
	}
	
	public int get(T obj) {
		int c = 0;
		for(Node<T> current = head; current != null; current = current.nextNode, c++) {	
			if(current.data == obj) return c;
		}
		return -1;
	}
	
	public boolean contains(T object) {
		for(Node<T> current = head; current != null; current = current.nextNode) {
			if(current.data == object) return true;
		}
		return false;
	}
	
	public T getLast() {
		return last.data;
	}
	
	public T getFirst() {
		return head.data;
	}
	
	public boolean remove(T object) {
		int i = 0;
		
		for(Node<T> current = head; current != null; current = current.nextNode, i++) {
			if(current.data == object) return remove(i);
		}
		return false;
	}
	
	public boolean remove(int index) {
		boolean success = false;
		Node<T> removed = (index == size - 1 ? last : getNode(index));
	
		if(isEmpty()) success = false;
		else if(index >= size) success = false;
		else if(index == 0) {
			head = head.nextNode;
			success = true;
		}
		else if(removed == last) {
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
	
	public void removeIf(MyPredicate<T> condition) {
		for (int i = 0; i < this.size(); i++) {
			if(condition.filter(this.get(i))) {
				if(remove(i))
					i--;
			}
		}
	}
	
	private Node<T> getNode(int i) {
		if(i == 0) return head;
		int c = 0;
		
		for(Node<T> current = head; current != null; c++, current = current.nextNode) {
			if(c == i) return current;
		}
		return null;
	}
	
	public void sort(MyComparator<T> comparator) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i == j) continue;
				else if(comparator.compare(get(i), get(j)) < 0){
					swapItems(i, j);
				}
			}
		}
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
		
		
		for(Node<T> current = head; current != null; current = current.nextNode) {
			str.append(current.data.toString() + (current.nextNode != null ? "; " : ""));
		}
		
		return str.append("]").toString();
	}
	
	public void swapItems(int firstIndex, int secondIndex) {
		if(firstIndex >= size || secondIndex >= size) return;
		if(firstIndex == secondIndex) return;
		
		T data = get(firstIndex);
		getNode(firstIndex).data = getNode(secondIndex).data;
		getNode(secondIndex).data = data;
	}
}
