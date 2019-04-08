package br.com.aps_so.testes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import br.com.aps_so.interfaces.MyPredicate;
import br.com.aps_so.lists.*;
import br.com.aps_so.process_managers.Process;

public class Tests {
	public static void main(String[] args) throws FileNotFoundException {
//		Queue<Process> wait = getFileInfo(new File("C:\\Users\\nescara\\Documents\\processes.txt"));
//		Queue<Process> ready = new Queue<>();
//		
//		ready.add(wait.get(0));
//		ready.add(wait.get(1));
//		ready.add(wait.get(2));
//		
//		wait.removeIf(new MyPredicate<Process>() {
//			
//			@Override
//			public boolean filter(Process t) {
//				if(ready.contains(t)) {
//					return true;
//				}
//				return false;
//			}
//		});
//		Process p = new Process("dasd", 0);
//		Process p2 = new Process("dassd", 0);
//		Process p3 = new Process("dsda", 0);
//
//		System.out.println(wait.toString());
		Pattern p = Pattern.compile("[A-Z]:\\\\[Uu]sers\\\\(\\w*)");
		Matcher m = p.matcher(System.getProperty("user.dir"));
		System.out.println(System.getProperty("user.dir"));
		System.out.println(p.toString());
		if(m.find()) {
			System.out.println(m.group(1));
		}
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
