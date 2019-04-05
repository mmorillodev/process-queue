package br.com.aps_so.lists;


import br.com.aps_so.interfaces.*;

public class MyLinkedList<T extends Object> {
	private Node<T> head;
	private Node<T> tail;
	private int size;
	
	public MyLinkedList(){
		
	}
	
	public void add(T data) {
		if(isEmpty()) {
			head = new Node<T>(data);
			tail = head;
			size++;
			return;
		}
		tail.nextNode = new Node<T>(data);
		tail.nextNode.previousNode = tail;
		tail = tail.nextNode;
		size++;
	}
	
	public void add(T data, int position) {
		if(position < 0) return;
		Node <T> node = new Node<T>(data);
		
		if(position == 0) {
			node.nextNode = head;
			head.previousNode = node;
			head = node;
		}
		else if(position == size) {
			add(data);
		}
		else {
			node.nextNode = getNodeAt(position);
			getNodeAt(position).previousNode = node;
			getNodeAt(position-1).nextNode = node;
		}
		size++;
	}
	
	public T pop() {
		T data = null;
		if(isEmpty()) return data;
		if(tail.previousNode != null) {
			data = tail.data;
			tail = tail.previousNode;
			
			tail.nextNode = null;
			
			size--;
		}
		else{
			data = head.data;
			head = null;
			size--;
		}
		return data;
	}
	
	public T removeFirst() {
		if(isEmpty()) return null;
		T statement = head.data;
		head = head.nextNode;
		return statement;
	}
	
	public void forEach(MyConsumer<T> consumer) {
		for(int i = 0; i < size; i++) {
			consumer.action(get(i));
		}
	}
	
	public void removeIf(MyPredicate<T> condition) {
		forEach(new MyConsumer<T>() {
			int i = 0;
			@Override
			public void action(T value) {
				if(condition.filter(value))
					removeAt(i);
				i++;
			}
		});
	}
	
	public T peek() {
		return head.data;
	}
	
	public T getLast() {
		return tail.data;
	}
	
	//TODO
	public void removeAt(int position) {
		if(isEmpty()) return;
		if(position >= size) return;
		
		Node<T> excluded;
		
		if(position == 0)
			excluded = head;
		else if(position == size-1)
			excluded = tail;
		else
			excluded = getNodeAt(position);
		
		if(excluded != tail)
			excluded.nextNode.previousNode = excluded.previousNode;
		else
			tail = tail.previousNode;
		
		if(excluded != head)
			excluded.previousNode.nextNode = excluded.nextNode;
		else
			head = head.nextNode;
		
		size--;
	}
	
	public T get(int position) {
		return getNodeAt(position).data;
	}
	
	public int search(T target) {
		if(isEmpty()) return -1;
		Node<T> current = head;
		int cont = 0;
		
		 while(current != null){
			if(current.data.equals(target))
				return cont;
			current = current.nextNode;
			cont ++;
		}
		return -1;
	}
	
	public int size() {
		return size;
	}
	
	public void sort() {
		MyComparator<T> comparator = new MyComparator<T>() {};
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i == j) continue;
				else if(comparator.compare(get(i), get(j)) < 0){
					swapItems(i, j);
				}
			}
		}
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
	
	public void swapItems(int firstIndex, int secondIndex) {
		if(firstIndex >= size || secondIndex >= size) return;
		if(firstIndex == secondIndex) return;
		
		T data = get(firstIndex);
		getNodeAt(firstIndex).data = getNodeAt(secondIndex).data;
		getNodeAt(secondIndex).data = data;
	}
	
	public Object[] toArray() {
		Object[] arr = new Object[size];
		forEach(new MyConsumer<T>() {
			int cont = 0;
			@Override
			public void action(T value) {
				arr[cont] = value;
				cont++;
			}
		});
		return arr;
	}
	
	public T[] toArray(T[] arr) {
		forEach(new MyConsumer<T>() {
			int cont = 0;
			@Override
			public void action(T value) {
				arr[cont] = value;
			}
		});
		return arr;
	}
	
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}
	
	public static MyLinkedList<Object> asList(Object... args){
		MyLinkedList<Object> list = new MyLinkedList<>();
		for (Object object : args) {
			list.add(object);
		}
		return list;
	}
	
	public static MyLinkedList<Integer> asList(Integer... args){
		MyLinkedList<Integer> list = new MyLinkedList<>();
		for (Integer object : args) {
			list.add(object);
		}
		return list;
	}
	
	public static MyLinkedList<Character> asList(Character... args){
		MyLinkedList<Character> list = new MyLinkedList<>();
		for (Character object : args) {
			list.add(object);
		}
		return list;
	}
	
	public static MyLinkedList<Double> asList(Double... args){
		MyLinkedList<Double> list = new MyLinkedList<>();
		for (Double object : args) {
			list.add(object);
		}
		return list;
	}
	
	public static MyLinkedList<String> asList(String... args){
		MyLinkedList<String> list = new MyLinkedList<>();
		for (String object : args) {
			list.add(object);
		}
		return list;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private Node<T> getNodeAt(int position){
		Node<T> current = head;
		
		for(int i = 0; i < position; i++) {
			current = current.nextNode;
		}
		return current;
	}
}

class Node<T extends Object> {
	T data;
	Node<T> nextNode;
	Node<T> previousNode;
	
	Node(T data) {
		this.data = data;
	}
}
