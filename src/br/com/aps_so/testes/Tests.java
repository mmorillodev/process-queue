package br.com.aps_so.testes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import br.com.aps_so.lists.*;
import br.com.aps_so.process_managers.Main;
import br.com.aps_so.process_managers.Process;

public class Tests {
	public static void main(String[] args) throws FileNotFoundException {
		Queue<Process> queue = getFileInfo(new File("C:\\Users\\mathe\\Documents\\processes.txt"));
		queue.forEach(System.out::println);
		System.out.println(queue.size());
		
	}
	
	public static Queue<Process> getFileInfo(File file) throws FileNotFoundException {
		Scanner fileDatas = new Scanner(file);
		MyList<String> aux = new MyList<>();
		Queue<Process> readyQueue = new Queue<>();
		
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
}
