package br.com.aps_so.process_managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;

public class Main {
	private final String BASE_PATH = "C:\\Users\\nescara\\Documents\\";
	private final int QUANTUM = 2;
	private final long QUANTUM_MILIS = 0;
	private Queue<Process> readyQueue;
	
	public static void main(String[] args) throws IOException {
		println("===============================================================================\n");
		println("\t\t\t\tRound Robin");
		println("\n===============================================================================");
		try {
			new Main().deploy();
		} catch(FileNotFoundException e) {
			print("File not found: " + e.getMessage());
		}
		
	}
	
	public void deploy() throws FileNotFoundException {
		readyQueue = new Queue<>();
		
		getFileInfo(new File(BASE_PATH + "\\processes.txt"));
		
		Scheduler manager = new Scheduler(readyQueue, QUANTUM, QUANTUM_MILIS);
		
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
	
	public static void print(String str) {System.out.print(str);}
	public static void println(String str) {System.out.println(str);}
	public static void println() {System.out.println();}
}