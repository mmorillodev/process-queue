package br.com.aps_so.testes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import br.com.aps_so.interfaces.MyPredicate;
import br.com.aps_so.lists.*;
import br.com.aps_so.process_managers.Process;

public class Tests {
	public static void main(String[] args) throws FileNotFoundException {
		Queue<Process> queue = getFileInfo(new File("C:\\Users\\nescara\\Documents\\processes.txt"));
		Queue<Process> outra = new Queue<>();
		
		Process current = queue.getFirst();
		outra.add(queue.get(0));
		outra.add(queue.get(1));
		outra.add(queue.get(2));
		
		outra.removeIf(new MyPredicate<Process>() {
			
			@Override
			public boolean filter(Process t) {
				if(queue.contains(t)) {
					return true;
				}
				return false;
			}
		});
		
		
		System.out.println(outra.toString());
		
		Queue<Integer> nums = new Queue<>();
		
		nums.add(1);
		nums.add(2);
		nums.add(3);
		nums.add(4);
		nums.add(5);
		
		nums.removeIf(new MyPredicate<Integer>() {

			@Override
			public boolean filter(Integer t) {
				if(t % 2 == 0) return true;
				return false;
			}
		});
		System.out.println(nums.toString());
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
		fileDatas.close();
		return readyQueue;
	}
}
