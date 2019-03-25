import java.io.IOException;
import java.util.Scanner;
import interfaces.OnProcessChangeListener;

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
		Scanner quantumEntry = new Scanner(System.in);
		
		print("Type the quantum for each process: ");
		int quantum = quantumEntry.nextInt();		

		Scheduler manager = new Scheduler(readyQueue, quantum, QUANTUM_MILIS);
		manager.setOnProcessChangeListener(new onProcessChangeListener(){
			@Override
			public void OnChange(Process newProcess){
				println("=======New process entry.========");
				println(newProcess.toString());
			}
		});
		manager.start();
	}
	
	public static void print(String str) {System.out.print(str);}
	public static void println(String str) {System.out.println(str);}
}