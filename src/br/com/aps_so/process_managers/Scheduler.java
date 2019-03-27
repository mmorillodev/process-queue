package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.OnProcessChangeListener;
import br.com.aps_so.lists.Queue;

public class Scheduler extends Thread{
	private int quantum;
	private long quantum_milis;
	private OnProcessChangeListener onChangeCallback;
	private Runnable onDeployThreadListener;sadsadsad
	private Queue<Process> queue;
	
	public Scheduler(Queue<Process> queue, int quantum, long quantum_milis) {
		this.queue = queue;
		this.quantum = quantum;
		this.quantum_milis = quantum_milis;
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
		
	}
}