package br.com.aps_so.process_managers;

import java.util.Scanner;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.OnProcessChangeListener;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread implements MyComparator<Process>{
	private int quantum;
	private int currentQuantum;
	private long quantumMilis;
	private OnProcessChangeListener onChangeCallback;
	private Runnable onDeployThreadListener;
	private Queue<Process> queue;
	
	public Scheduler(Queue<Process> queue, int quantum, long quantumMilis) {
		this.queue = queue;
		this.quantum = quantum;
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
		for(Process current = queue.unQueue(); !queue.isEmpty(); current = queue.unQueue()) {
			onChangeCallback.onChange(current);
			while(currentQuantum % quantum != 0 || first) {
				System.out.println(currentQuantum);
				if(current.hasIO()) {
					if(current.getIOIntervals().get(0) == currentQuantum) {
						final Process current_ = current;
						new Thread(new Runnable() {
							@Override
							public void run() {
								io.next();
								current_.getIOIntervals().remove(0);
							}
						}).start();
					}
				}
				first = false;
				delay(quantumMilis);
				currentQuantum++;
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