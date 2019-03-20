
public class Main {
	public static void main(String[] args) {
		Queue<Integer> q = new Queue<>();
		
		q.add(1);
		q.add(2);
		q.add(3);
		q.add(4);
		
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
	}
}
