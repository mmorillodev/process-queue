package br.com.aps_so.process_managers;

import  br.com.aps_so.lists.*;

public class Process{
	private String name;
	private int priority;
	private boolean hasIO;
	private Queue<Integer> ioArrivals;
	private int duration, arrival;
	
	public Process(MyList<String> fileValues) {
		setName(fileValues.get(0));
		setDuration(Integer.parseInt(fileValues.get(1)));
		setArrival(Integer.parseInt(fileValues.get(2)));
		hasIO(fileValues.get(3).equalsIgnoreCase("SIM") ? true : false);
		if(hasIO()) {
			setIOIntervals(toIntArray(fileValues.get(4).split(" ")));
		}
		else
			ioArrivals = new Queue<>();
	}
	
	public Process(String name, int priority){
		this.name = name;
		this.priority = priority;
	}
	
	public void hasIO(boolean hasIO) {
		this.hasIO = hasIO;
	}
	
	public boolean hasIO() {
		return hasIO;
	}
	
	public void setArrival(int arrival) {
		this.arrival = arrival;
	}
	
	public int getArrival() {
		return arrival;
	}
	
	public void setIOIntervals(Integer[] intervals) {
		if(hasIO)
			this.ioArrivals = new Queue<>(intervals);
	}
	
	public Queue<Integer> getIOIntervals() {
		return ioArrivals;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPiority(int priority) {
		this.priority = priority;
	}
	
	public String toString() {
		return "Name: " + name + 
				", Priority: " + priority + 
				", Arrival: " + arrival + 
				", Duration: " + duration + 
				", Has I/O: " + hasIO + 
				", I/O moments: " + arrToString();
	}

	public int getPriority() {
		return priority;
	}
	
	private String arrToString() {
		StringBuffer str = new StringBuffer();
		str.append("[");
		for(int i = 0; i < ioArrivals.size(); i++) {
			str.append(ioArrivals.get(i) + (i == ioArrivals.size()-1 ? "" : ", "));
		}
		return str.append("]").toString();
	}
	
	private Integer[] toIntArray(String[] arr) {
		Integer[] newArr = new Integer[arr.length];
		
		for(int i = 0; i < newArr.length; i++) {
			newArr[i] = Integer.parseInt(arr[i]);
		}
		
		return newArr;
	}
}
