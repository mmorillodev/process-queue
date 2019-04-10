package br.com.aps_so.lists;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.MyConsumer;
import br.com.aps_so.interfaces.MyPredicate;
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
	
	public synchronized void add(T value) {
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
	
	public synchronized boolean addIfNotExist(T value) {
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
	
	public T get(int i) {
		if(i >= size || i < 0) return null;
		if(i == 0) return head.data;
		if(i == size-1) return last.data;
		int c = 1;
				
		for(Node<T> current = head.nextNode; current != null; current = current.nextNode, c++) {	
			if(c == i) return current.data;
		}
		
		return null;
	}
	
	public int get(T obj) {
		if(obj == null) return -1;
		if(obj == head.data) return 0;
		if(obj == last.data) return size-1;
		
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
			last = removed.previousNode;
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
		if(i >= size) return null;
		if(i == 0) return head;
		if(i == size-1) return last;
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
	
	public synchronized void forEach(MyConsumer<T> consumer) {
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
	
	private void swapItems(int firstIndex, int secondIndex) {
		if(firstIndex >= size || secondIndex >= size) return;
		if(firstIndex == secondIndex) return;
		
		T data = get(firstIndex);
		
		getNode(firstIndex).data = getNode(secondIndex).data;
		getNode(secondIndex).data = data;
	}
}
