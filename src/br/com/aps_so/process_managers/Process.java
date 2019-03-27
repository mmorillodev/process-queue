package br.com.aps_so.process_managers;

import  br.com.aps_so.lists.*;

public class Process{
	private String name;
	private int priority;
	private boolean hasIO;
	private int[] ioArrivals;
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
			ioArrivals = new int[0];
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
	
	public void setIOIntervals(int[] intervals) {
		if(hasIO)
			this.ioArrivals = intervals;
	}
	
	public Process(String name, int priority){
		this.name = name;
		this.priority = priority;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public double getDuration() {
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
		for(int i = 0; i < ioArrivals.length; i++) {
			str.append(ioArrivals[i] + (i == ioArrivals.length-1 ? "" : ", "));
		}
		return str.append("]").toString();
	}
	
	private int[] toIntArray(String[] arr) {
		int[] newArr = new int[arr.length];
		
		for(int i = 0; i < newArr.length; i++) {
			newArr[i] = Integer.parseInt(arr[i]);
		}
		
		return newArr;
	}
}
