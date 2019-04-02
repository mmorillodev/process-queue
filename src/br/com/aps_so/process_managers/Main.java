package br.com.aps_so.process_managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import br.com.aps_so.interfaces.OnFinishProcessListener;
import br.com.aps_so.interfaces.OnProcessChangeListener;
import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;

public class Main implements OnProcessChangeListener, OnFinishProcessListener {
	private final String BASE_PATH = "C:\\Users\\nescara\\Documents\\";
	private final int QUANTUM = 2;
	private final long QUANTUM_MILIS = 0;
	private Queue<Process> readyQueue;
	
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
		readyQueue = new Queue<>();
		
		getFileInfo(new File(BASE_PATH + "\\processes.txt"));
		
		Scheduler manager = new Scheduler(readyQueue, QUANTUM, QUANTUM_MILIS);
		
		manager.setOnProcessChangeListener(this);
		manager.setOnProcessFinishListener(this);
		
		manager.start();
	}
	
	public void getFileInfo(File file) throws FileNotFoundException {
		Scanner fileDatas = new Scanner(file);
		MyList<String> currentProcess = new MyList<>();
		
		while(fileDatas.hasNext()) {
			String line = fileDatas.nextLine();
			if(line.equals("") || !fileDatas.hasNext()) {
				if(!fileDatas.hasNext()) currentProcess.push(line);
				readyQueue.add(new Process(currentProcess));
				currentProcess.clear();
				continue;
			}
			currentProcess.push(line);
		}
		fileDatas.close();
	}

	@Override
	public void onChange(Process newProcess, int currentTime, Queue<Process> readyQueue) {
		System.out.println("\nTempo " + currentTime + ":\n CPU -> " + newProcess.getName() + "\n Fila -> " + readyQueue.toString());
	}

	@Override
	public void onFinish(Process oldProcess, int currentTime) {
		System.out.println(" Fim do processo " + oldProcess.getName());
		
	}
}