package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.OnProcessChangeListener;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread{
	private int quantum;
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
		
		for(Process current = queue.unQueue(); current != null; current = queue.unQueue()) {
			delay(quantumMilis);
			onChangeCallback.onChange(current);
		}
	}
	
	private void delay(long timeMilis) {
		try {
			sleep(timeMilis);
		} catch(InterruptedException e) {
			delay(timeMilis);
		}
	}
}