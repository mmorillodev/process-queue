public class Main {
	public static void main(String[] args) {
		Queue<Process> q = new Queue<>();
		Process p1 = new Process("Process 1", 0);
		q.add(p1);
		q.add(new Process("Process 2", 1));
		q.add(new Process("Process 3", 0));
		q.add(new Process("Process 4", 2));
		
	}
}
