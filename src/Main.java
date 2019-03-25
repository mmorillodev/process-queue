import java.io.IOException;
import java.util.Scanner;
public class Main {
	private final String basePath = "C:\\Users\\Nescara\\Documents\\";
	
	public static void main(String[] args) throws IOException {
		new Main().deploy();
	}
	
	public void deploy() {
		Queue<Process> wait = new Queue<>();
		Queue<Process> ready = new Queue<>();
		Scanner quantumEntry = new Scanner(System.in);
		
		System.out.print("Type how long one quantum will take: ");
		int quantum = quantumEntry.nextInt();		
	}
	
	public void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
