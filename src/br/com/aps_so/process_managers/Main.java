package br.com.aps_so.process_managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.aps_so.lists.MyList;
import br.com.aps_so.lists.Queue;

public class Main implements Process.OnProcessStateChangeListeners {
	//Constante de quantum para cada process
	private final int QUANTUM = 4;
	
	public static void main(String[] args) throws IOException {
		System.out.println("\n********************************************\n");
		System.out.println("********* Escalonador Round-Robin **********\n");
		System.out.println("********************************************\n");
		
		try {
			//Inicia o processo
			new Main().deploy();
		} catch(FileNotFoundException e) {
			System.out.print("File not found: " + e.getMessage());
		}
		
	}
	
	public void deploy() throws FileNotFoundException {		
		//Compila uma expressão regex que será usado para pegar o path até a pasta documents do computador
		Pattern p = Pattern.compile("[A-Z]:\\\\([Uu]sers|[Uu]su.rios)\\\\(\\w*)");
		Matcher m = p.matcher(System.getProperty("user.dir"));
		
		//Instancia o gerenciador da fila de processos, passando a fila de processos e o quantum
		Scheduler manager;
		if(m.find()) 
			manager = new Scheduler(getFileInfo(new File(m.group(0) + "\\Documents\\processes.txt")), QUANTUM);
	
		else 
			manager = new Scheduler(getFileInfo(new File("C:\\Users\\mathe\\documents\\processes.txt")), QUANTUM);
			
		manager.setOnProcessStateChangeListeners(this);
		//Inicia o escalonador
		manager.start();
	}
	
	//Metodo responsável por extrair os dados do arquivo e converte-lo para uma fila de processos
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

	//Metodos implementados da interface Process.OnProcessStateChangeListeners,
	//possibilitando a passagem deste mesmo objeto ao Scheduler.setOnProcessStateChangeListeners.
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