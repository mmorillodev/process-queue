
public class Main {
	public static void main(String[] args) {
		Queue q = new Queue(5);
		System.out.println(q.isEmpty());
		q.add(new Process("1", 0));
		q.add(new Process("2", 0));
		q.add(new Process("3", 0));
		q.add(new Process("4", 0));
		q.add(new Process("5", 0));
		
		System.out.println(q.unQueue().getName());
		System.out.println(q.nextEmptyPos);
		System.out.println();
		
		System.out.println(q.unQueue().getName());
		System.out.println(q.nextEmptyPos);
		System.out.println(q.head);
		
		System.out.println();
		System.out.println(q.unQueue().getName());
		System.out.println(q.nextEmptyPos);
		System.out.println(q.head);
		
		System.out.println();
		System.out.println(q.unQueue().getName());
		System.out.println(q.nextEmptyPos);
		System.out.println(q.head);
		
		System.out.println();
		System.out.println(q.unQueue().getName());
		System.out.println(q.nextEmptyPos);
		System.out.println(q.head);
		
		System.out.println(q.isEmpty());
		q.add(new Process("added later", 0));
		
	}
}
