package br.com.aps_so.process_managers;

import br.com.aps_so.interfaces.MyComparator;
import br.com.aps_so.interfaces.MyPredicate;
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
			println("Atualizando request queue...");
			updateRequestQueue(totalTime);
			if(!requestQueue.isEmpty()) {
				currentProcess = requestQueue.unQueue();
				println("Processo retirado da request Queue para ser processado: " + currentProcess.getName());
				int condition = (currentProcess.getBrust() < quantum  ? currentProcess.getBrust() : quantum);
				for(int i = 0; i < condition; i++) {
					System.out.println("Time " + totalTime + " -> " + currentProcess.getName());
					currentProcess.setBrust(currentProcess.getBrust()-1);	
					delay();
					totalTime++;
				}
				println("Nova duração do processo " + currentProcess.getName() + ": " + currentProcess.getBrust());
				if(currentProcess.getBrust() > 0) {
					println("Processo recolocado na wait Queue: " + currentProcess.getName());
					waitQueue.add(currentProcess);
				}
				else
					println("Processo " + currentProcess.getName() + " ja finalizado.");
			}
			else if(requestQueue == null) {
				println("Deu ruim. request queue ta nula.");
			}
			else {
				println("Requeste queue ta vazia.");
				delay();
				totalTime++;
			}
		}
	}
	
	private void updateRequestQueue(int totalTime) {
		Process current;
		println("Tamanho da wait queue: " + waitQueue.size());
		for(int i = 0; i < waitQueue.size(); i++) {
			println("Index " + i);
			current = waitQueue.get(i);
			println("Processo sendo analisado: " + current.getName());
			if(current.getArrival() <= totalTime) {
				requestQueue.addIfNotExist(current);
				println("Já iniciou o processo " + current.getName() + " entao add na request queue.");
			}
			println("Fim do index " + i);
		}
		println("Request queue: " + requestQueue.toString());
		println("Wait queue: " + waitQueue.toString());
		waitQueue.removeIf(new MyPredicate<Process>() {
			@Override
			public boolean filter(Process t) {
				if(requestQueue.contains(t)) return true;
				return false;
			}
		});
		println("Wait queue apos removeif: " + waitQueue.toString());
		println("Fim da atualização da requuest queue. Novo tamanho da request Queue: " + requestQueue.size() + ". Tamanho da wait Queue: " + waitQueue.size());
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
	
	private void println(String str) {
		System.out.println(str);
	}
}