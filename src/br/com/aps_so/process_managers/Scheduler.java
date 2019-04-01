package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread implements MyComparator<Process>{
	private int quantum;
	private long quantumMilis;
	private Runnable onDeployThreadListener;
	private Queue<Process> requestQueue;
	private Queue<Process> waitQueue;
	private Queue<Process> ioQueue;
	
	public Scheduler(Queue<Process> waitQueue, int quantum, long quantumMilis) {
		this.waitQueue = waitQueue;
		this.quantum = quantum;
		this.quantumMilis = quantumMilis;
		requestQueue = new Queue<>();
	}
	
	public void setOnDeployThreadListener(Runnable runnable) {
		this.onDeployThreadListener = runnable;
	}
	
	@Override
	public void run() {
		if(onDeployThreadListener != null) {
			onDeployThreadListener.run();
			return;	
		}
		
		waitQueue.sort(this);
		
		int totalTime = 0;
		Process currentProcess;
		
		while(!(waitQueue.isEmpty() && requestQueue.isEmpty())) {
			updateRequestQueue(totalTime);
			if(!requestQueue.isEmpty()) {
				currentProcess = requestQueue.unQueue();
				for(int i = 0; i < (currentProcess.getBrust() < quantum  ? currentProcess.getBrust() + 1 : quantum); i++) {
					System.out.println("Time " + totalTime + " -> " + currentProcess.getName());
					currentProcess.setBrust(currentProcess.getBrust()-1);
					delay();
					totalTime++;
				}
				
				if(currentProcess.getBrust() > 0) {
					waitQueue.add(currentProcess);
				}
			}
			else {
				delay();
				totalTime++;
			}
		}
	}
	
	private void updateRequestQueue(int totalTime) {
		Process current;
		
		for(int i = 0; i < waitQueue.size(); i++) {
			current = waitQueue.get(i);
			if(current.getArrival() <= totalTime) {
				if(requestQueue.addIfNotExist(current)) {
					waitQueue.remove(current);
				}
			}
		}
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