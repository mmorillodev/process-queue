package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.MyConsumer;
import br.com.aps_so.interfaces.MyPredicate;
import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread implements MyComparator<Process>{
	private int quantum, acmWait, acmTurnAround;
	private long quantumMilis;
	private Queue<Process> requestQueue;
	private Queue<Process> waitQueue;
	private Queue<Process> ioQueue;
	private MyList<Process> finished;
	
	public Scheduler(Queue<Process> waitQueue, int quantum, long quantumMilis) {
		this.waitQueue = waitQueue;
		this.quantum = quantum;
		this.quantumMilis = quantumMilis;
		requestQueue = new Queue<>();
		finished = new MyList<>();
		acmWait = 0;
		acmTurnAround = 0;
	}
	
	@Override
	public void run() {
		waitQueue.sort(this);
		
		int totalTime = 0;
		Process currentProcess;
		
		while(!(waitQueue.isEmpty() && requestQueue.isEmpty())) {
			updateRequestQueue(totalTime);
			
			if(!requestQueue.isEmpty()) {
				currentProcess = requestQueue.unQueue();
				
				int condition = (currentProcess.getBrust() < quantum  ? currentProcess.getBrust() : quantum);
//				int i = Math.abs(currentProcess.getBrust() - quantum);
				for(int i = 0; i < condition; i++) {
					updateWaitTime(currentProcess);
					System.out.println("Time " + totalTime + " -> " + currentProcess.getName());
					currentProcess.setBrust(currentProcess.getBrust()-1);	
					delay();
					totalTime++;
				}
				if(currentProcess.getBrust() > 0) {
					waitQueue.add(currentProcess);
				}
				else {
					currentProcess.setTurnAround(totalTime - currentProcess.getArrival());
					finished.push(currentProcess);
				}
			}
			else {
				delay();
				totalTime++;
			}
		}
		
		
		finished.forEach(new MyConsumer<Process>() {

			@Override
			public void action(Process value) {
				System.out.println("Process " + value.getName() + " waiting time: " + value.getWaitTime() + "; turn around: " + value.getTurnAround());
				acmWait += value.getWaitTime();
				acmTurnAround += value.getTurnAround();
			}
		});
		
		System.out.println("Média de waiting time: " + acmWait/finished.length() + "\nMédia de turnAround: " + acmTurnAround/finished.length());
	}
	
	private void updateRequestQueue(int totalTime) {
		waitQueue.forEach(new MyConsumer<Process>() {

			@Override
			public void action(Process current) {
				if(current.getArrival() <= totalTime) {
					requestQueue.addIfNotExist(current);
				}
			}
		});
		
		waitQueue.removeIf(new MyPredicate<Process>() {
			@Override
			public boolean filter(Process t) {
				if(requestQueue.contains(t)) return true;
				return false;
			}
		});
	}
	
	private void updateWaitTime(Process currentProcess) {
		requestQueue.forEach(new MyConsumer<Process>() {
			
			@Override
			public void action(Process value) {
				if(value == currentProcess) return;
				value.setWaitTime(currentProcess.getWaitTime()+1);
			}
		});
	}
	
	@Override
	public int compare(Process p1, Process p2) {
		if(p1.getArrival() > p2.getArrival()) return 1;
		else if (p1.getArrival() < p2.getArrival()) return -1;
		return 0;
	}
	
	private void delay() {
		try {
			sleep(quantumMilis);
		} catch(InterruptedException e) {}
	}
}