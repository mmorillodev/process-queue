
public class Main {
	public static void main(String[] args) {
		Queue<Integer> q = new Queue<>(5) {{
			add(5);
			add(4);
			add(3);
			add(2);
			add(1);
		}};
		
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		q.add(6);
		q.add(7);
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.unQueue());
		System.out.println(q.nextEmptyPos);
		System.out.println(q.head);
	}
}
