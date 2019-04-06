package br.com.aps_so.process_managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;

public class Main implements Process.OnProcessStateChangeListeners {
	private final String BASE_PATH = "C:\\Users\\mathe\\Documents\\";
	private final int QUANTUM = 4;
	
	public static void main(String[] args) throws IOException {
		System.out.println("\n********************************************\n");
		System.out.println("********* Escalonador Round-Robin **********\n");
		System.out.println("********************************************\n");
		try {
			new Main().deploy();
		} catch(FileNotFoundException e) {
			System.out.print("File not found: " + e.getMessage());
		}
		
	}
	
	public void deploy() throws FileNotFoundException {		
		Scheduler manager = new Scheduler(getFileInfo(new File(BASE_PATH + "\\processes.txt")), QUANTUM);
		
		manager.setOnProcessStateChangeListeners(this);
		
		manager.start();
	}
	
	public Queue<Process> getFileInfo(File file) throws FileNotFoundException {
		Scanner fileDatas = new Scanner(file);
		MyList<String> currentProcess = new MyList<>();
		Queue<Process> queue = new Queue<>();
		
		while(fileDatas.hasNext()) {
			String line = fileDatas.nextLine();
			if(line.equals("") || !fileDatas.hasNext()) {
				if(!fileDatas.hasNext()) currentProcess.push(line);
				queue.add(new Process(currentProcess));
				currentProcess.clear();
				continue;
			}
			currentProcess.push(line);
		}
		fileDatas.close();
		return queue;
	}

	@Override
	public void onExecuting(Process newProcess, int currentTime, Queue<Process> readyQueue) {
		System.out.println("\nTempo " + currentTime + ":\n CPU -> " + newProcess.getName() + "\n Fila -> " + readyQueue.toString());
	}

	@Override
	public void onFinish(Process oldProcess) {
		System.out.println(" Fim do processo " + oldProcess.getName());
		
	}

	@Override
	public void onInterruptedByIO(String processName) {
		System.out.println(" Operação de I/O de " + processName);
	}
}