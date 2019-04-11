package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.MyComparator;
import  br.com.aps_so.lists.*;
import br.com.aps_so.process_managers.Process;

public class Process{
	private String name;
	private boolean hasIO;
	private Queue<Integer> ioArrivals;
	private int remainingBrust, arrival, priority, waitTime, turnAround, totalBrust;
	
	public Process(MyList<String> fileValues) {
		setName(fileValues.get(0));
		setBrust(Integer.parseInt(fileValues.get(1)));
		setRemainingBrust(getBrust());
		setArrival(Integer.parseInt(fileValues.get(2)));
		hasIO(fileValues.get(3).equalsIgnoreCase("SIM") ? true : false);
		if(hasIO()) {
			setIOIntervals(toIntArray(fileValues.get(4).split(" ")));
			ioArrivals.sort(new MyComparator<Integer>() {
				@Override
				public int compare(Integer str, Integer str2) {
					if(str > str2) return 1;
					if(str < str2) return -1;
					return 0;
				}
			});
		}
		else
			ioArrivals = new Queue<>();
	}
	
	public Process(String name, int priority){
		this.name = name;
		this.priority = priority;
		ioArrivals = new Queue<>();
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
	
	public void setBrust(int duration) {
		totalBrust = duration;
	}
	
	public int getBrust() {
		return totalBrust;
	}
	
	public void setRemainingBrust(int duration) {
		this.remainingBrust = duration;
	}
	
	public int getRemainingBrust() {
		return remainingBrust;
	}
	
	public void setTurnAround(int turnAround) {
		this.turnAround = turnAround;
	}
	
	public int getTurnAround() {
		return turnAround;
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
	
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public String toString() {
		return name + " (" + remainingBrust + ")";
	}

	public int getPriority() {
		return priority;
	}
	
	private Integer[] toIntArray(String[] arr) {
		Integer[] newArr = new Integer[arr.length];
		
		for(int i = 0; i < newArr.length; i++) {
			newArr[i] = Integer.parseInt(arr[i]);
		}
		
		return newArr;
	}
	
	public interface OnProcessStateChangeListeners{
		public void onFinish(Process oldProcess);
		public void onExecuting(Process newProcess, int currentTime, Queue<Process> raedyQueue);
		public void onInterruptedByIO(String processName);
	}
}
