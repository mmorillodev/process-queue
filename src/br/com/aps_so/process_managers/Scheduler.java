package br.com.aps_so.process_managers;

import java.util.Scanner;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.OnProcessChangeListener;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread implements MyComparator<Process>{
	private int quantum4process;
	private long quantumMilis;
	private OnProcessChangeListener onChangeCallback;
	private Runnable onDeployThreadListener;
	private Queue<Process> queue;
	
	public Scheduler(Queue<Process> queue, int quantum, long quantumMilis) {
		this.queue = queue;
		this.quantum4process = quantum;
		this.quantumMilis = quantumMilis;
	}
	
	public void setOnProcessChangeListener(OnProcessChangeListener onChangeCallback) {
		this.onChangeCallback = onChangeCallback;
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
		
		queue.sort(this);
		
		Scanner io = new Scanner(System.in);
		boolean first = true;
		int currentQuantum = 0;
		int quantumAux = 1;
		
		for(Process current = queue.unQueue(); !queue.isEmpty(); current = queue.unQueue()) {
			onChangeCallback.onChange(current);
			quantumAux = 0;
			currentQuantum++;
			first = true;
			
			while(currentQuantum % quantum4process != 0 || first) {
				System.out.println(currentQuantum);
				if(current.getArrival() < currentQuantum);
				if(quantumAux >= current.getDuration()) {
					current.setDuration(current.getDuration()-quantum4process);
					System.out.println(current.getDuration());
					break;
				}
				else if(current.hasIO() && !current.getIOIntervals().isEmpty()) {
					if(current.getIOIntervals().get(0) == currentQuantum) {
						current.getIOIntervals().unQueue();
						queue.add(current);
					}
				}
				first = false;
				delay(quantumMilis);
				quantumAux++;
				currentQuantum++;
			}
			
		}
		io.close();
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