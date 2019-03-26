import java.io.IOException;
import java.util.Scanner;

public class Main {
	private final String BASE_PATH = "C:\\Users\\Nescara\\Documents\\";
	public final long QUANTUM_MILIS = 3500;
	
	public static void main(String[] args) throws IOException {
		println("=============================================");
		println("\t\tRound Robin\t\t");
		println("=============================================");
		new Main().deploy();
	}
	
	public void deploy() {
		Queue<Process> readyQueue = new Queue<>();
		
		print("Type the quantum for each process: ");
		int quantum = new Scanner(System.in).nextInt();		

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
	
	public static void print(String str) {System.out.print(str);}
	public static void println(String str) {System.out.println(str);}
}