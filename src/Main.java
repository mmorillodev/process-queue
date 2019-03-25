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
		Queue<Process> wait = new Queue<>();
		Queue<Process> ready = new Queue<>();
		Scanner quantumEntry = new Scanner(System.in);
		
		print("Type the quantum for each process: ");
		int quantum = quantumEntry.nextInt();		
	}
	
	public static void print(String str) {System.out.print(str);}
	public static void println(String str) {System.out.println(str);}
}