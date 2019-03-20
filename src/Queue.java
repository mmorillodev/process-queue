public class Queue<T> {
	public int head;
	public int nextEmptyPos;
	public int absSize;
	public T[] arr;
	
	@SuppressWarnings("unchecked")
	
	public Queue(int initialCapacity) {
		if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
		
		arr = (T[])new Object[initialCapacity];
		head = 0;
		nextEmptyPos = 0;
	}
	
	public Queue() {
		this(10);
	}
	
	public void add(T value) {
		if(isFull()) return;
		if(nextEmptyPos == arr.length);
		arr[nextEmptyPos] = value;
		nextEmptyPos = checkPosition(nextEmptyPos+1);
		absSize++;
	}
	
	public T unQueue() {
		if(isEmpty()) return null;
		T value = arr[head];
		head = checkPosition(head+1);
		absSize--;
		return value;
	}
	
	public T get(int i) {
		return arr[i];
	}
	
	public int size() {
		return absSize;
	}
	
	public boolean isEmpty() {
		return absSize == 0;
	}
	
	public boolean isFull() {
		return absSize == arr.length;
	}
	
	private int checkPosition(int index) {
		if(index < 0) return arr.length-1;
		if(index >= arr.length) return 0;
		else return index;
	}
}
