public class Queue {
	public int head;
	public int nextEmptyPos;
	public int capacity;
	public int size;
	public Process[] processes;
	
	public Queue(int size) {
		processes = new Process[size];
		capacity = size;
		head = 0;
		nextEmptyPos = 0;
	}
	
	public void add(Process process) {
		if(size < capacity) {
			processes[nextEmptyPos++] = process;
		}
	}
	
	public Process unQueue() {
		if(head == capacity) head = 0;
		if(nextEmptyPos == capacity) nextEmptyPos = head;
		return processes[head++];
	}
	
	public int size() {
		return absolute(nextEmptyPos-head);
	}
	
	public boolean isFull() {
		throw new UnsupportedOperationException();
	}
	
	public boolean isEmpty() {
		return head == nextEmptyPos;
	}
	
	public int absolute(int value) {
		if(value < 0)
			return -value;
		return value;
	}
}
