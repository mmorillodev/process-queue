package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.MyConsumer;
import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread implements MyComparator<Process>{
	private int quantum, acmWait, acmTurnAround;
	private Process.OnProcessStateChangeListeners changeCallback;
	private Queue<Process> readyQueue, waitQueue;
	private MyList<Process> finished;
	
	public Scheduler(Queue<Process> waitQueue, int quantum) {
		this.waitQueue = waitQueue;
		this.quantum = quantum;
		readyQueue = new Queue<>();
		finished = new MyList<>();
		acmWait = 0;
		acmTurnAround = 0;
	}
	
	public void setOnProcessStateChangeListeners(Process.OnProcessStateChangeListeners changeCallback) {
		this.changeCallback = changeCallback;
	}
	
	@Override
	public void run() {
		waitQueue.sort(this);
		
		int totalTime = 0;
		Process currentProcess;
		
		while(!(waitQueue.isEmpty() && readyQueue.isEmpty())) {
			updateRequestQueue(totalTime);
			if(!readyQueue.isEmpty()) {
				currentProcess = readyQueue.unQueue();
				
				int condition = (currentProcess.getRemainingBrust() < quantum  ? currentProcess.getRemainingBrust() : quantum);
				
				for(int i = 0; i < condition; i++) {
					updateRequestQueue(totalTime);
					if(currentProcess.hasIO() && currentProcess.getIOIntervals().size() > 0) {
						if(currentProcess.getBrust() - currentProcess.getRemainingBrust() == currentProcess.getIOIntervals().getFirst()) {
							changeCallback.onInterruptedByIO(currentProcess.getName());
							currentProcess.getIOIntervals().unQueue();
							waitQueue.add(currentProcess);
							break;
						}
					}
					
					currentProcess.setRemainingBrust(currentProcess.getRemainingBrust()-1);
					changeCallback.onExecuting(currentProcess, totalTime++, readyQueue);
				}
				if(currentProcess.getRemainingBrust() > 0) {
					waitQueue.add(currentProcess);
				}
				else {
					currentProcess.setWaitTime(totalTime - (currentProcess.getArrival() + currentProcess.getBrust()));
					currentProcess.setTurnAround(totalTime - currentProcess.getArrival());
					
					changeCallback.onFinish(currentProcess);
					
					finished.push(currentProcess);
				}
			}
			
			else 
				totalTime++;
		}
		
		System.out.println("\n*****************************************\n");
		System.out.println("* Encerrando simulacao de escalonamento *\n");
		System.out.println("*****************************************\n");
		
		finished.forEach(new MyConsumer<Process>() {

			@Override
			public void action(Process value) {
				System.out.println("Process " + value.getName() + ":\n Waiting time: " + value.getWaitTime() + "; turn around: " + value.getTurnAround() + "\n");
				acmWait += value.getWaitTime();
				acmTurnAround += value.getTurnAround();
			}
		});
		
		System.out.println("\nMédia de waiting time: " + acmWait/finished.length() + "\nMédia de turnAround: " + acmTurnAround/finished.length());
	}
	
	private void updateRequestQueue(int totalTime) {
		Process current;
		for(int i = 0; i < waitQueue.size(); i++) {
			current = waitQueue.get(i);
			if(current.getArrival() <= totalTime) {
				readyQueue.addIfNotExist(current);
				waitQueue.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public int compare(Process p1, Process p2) {
		if(p1.getArrival() > p2.getArrival()) return 1;
		else if (p1.getArrival() < p2.getArrival()) return -1;
		return 0;
	}
}