import java.io.IOException;
import java.util.Scanner;
public class Main {
	private static final String basePath = "C:\\Users\\Nescara\\Documents\\";
	public static void main(String[] args) throws IOException {
		Queue<Process> wait = new Queue<>();
		Queue<Process> ready = new Queue<>();
		Scanner quantumEntry = new Scanner(System.in);
		
		int quantum = quantumEntry.nextInt();
		
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
