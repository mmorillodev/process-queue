package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread implements MyComparator<Process>{
	private int quantum4process;
	private long quantumMilis;
	private Runnable onDeployThreadListener;
	private Queue<Process> readyQueue;
	private Queue<Process> ioQueue;
	
	public Scheduler(Queue<Process> queue, int quantum, long quantumMilis) {
		this.readyQueue = queue;
		this.quantum4process = quantum;
		this.quantumMilis = quantumMilis;
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
		
		readyQueue.sort(this);
		
		int totalQuantum = -1;
		int currentQuantum;
		
		for(Process current = readyQueue.unQueue(); current != null; current = readyQueue.unQueue()) {
//			onChangeCallback.onChange(current);
			currentQuantum = 0;
		
			while(currentQuantum != current.getDuration()) {
				delay(quantumMilis);
				totalQuantum++;
				System.out.println("Time " + totalQuantum);
				
				if(quantum4process == currentQuantum) {
					current.setDuration(current.getDuration() - currentQuantum);
					readyQueue.add(current);
					break;
				}
				else if(current.getArrival() <= totalQuantum) {
					System.out.println(current.toString());
					currentQuantum++;
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
	
	private void delay(long timeMilis) {
		try {
			sleep(timeMilis);
		} catch(InterruptedException e) {
			delay(timeMilis);
		}
	}
}