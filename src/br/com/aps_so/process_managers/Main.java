package br.com.aps_so.process_managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;
import br.com.aps_so.interfaces.OnProcessChangeListener;

public class Main {
	private final String BASE_PATH = "C:\\Users\\mathe\\Documents\\";
	private final long QUANTUM_MILIS = 3500;
	public Queue<Process> readyQueue;
	
	public static void main(String[] args) throws IOException {
		println("=============================================");
		println("\t\tRound Robin\t\t");
		println("=============================================");
		new Main().deploy();
	}
	
	public void deploy() throws FileNotFoundException {
		Scanner scanner = new Scanner(System.in);
		readyQueue = new Queue<>();
		getFileInfo(new File(BASE_PATH + "\\processes.txt"));
		
		print("Type the quantum for each process: ");
		int quantum = scanner.nextInt();
		scanner.close();

		Scheduler manager = new Scheduler(readyQueue, quantum, QUANTUM_MILIS);
		
		manager.setOnProcessChangeListener(new OnProcessChangeListener(){
			@Override
			public void onChange(Process newProcess){
				println("=======New process entry.========");
				println(newProcess.toString());
			}
		});
		manager.start();
	}
	
	public Queue<Process> getFileInfo(File file) throws FileNotFoundException {
		Scanner fileDatas = new Scanner(file);
		MyList<String> aux = new MyList<>();
		
		while(fileDatas.hasNext()) {
			String aux_ = fileDatas.nextLine();
			if(aux_.equals("") || !fileDatas.hasNext()) {
				if(!fileDatas.hasNext()) aux.push(aux_);
				readyQueue.add(new Process(aux));
				aux.clear();
				continue;
			}
			aux.push(aux_);
		}
		
		return readyQueue;
	}
	
	public static void print(String str) {System.out.print(str);}
	public static void println(String str) {System.out.println(str);}
}