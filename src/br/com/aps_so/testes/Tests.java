package br.com.aps_so.testes;

import br.com.aps_so.lists.*;

public class Tests {
	public static void main(String[] args) {
		Queue<Integer> queue = new Queue<>();
		MyLinkedList<Integer> mLinked = new MyLinkedList<>();
		MyList<Integer> mList = new MyList<>();
		
		queue.add(1);
		queue.add(2);
		queue.add(4);
		queue.add(5);
		
		mLinked.add(1);
		mLinked.add(2);
		mLinked.add(3);
		mLinked.add(4);
		
		
		mList.push(1);
		mList.push(2);
		mList.push(3);
		mList.push(4);
		
		for(int i = 0; i < 4; i++) {
			System.out.println("---------------");
			System.out.println(queue.unQueue());
			System.out.println("---------------");
			System.out.println(mLinked.pop());
			System.out.println("---------------");
			System.out.println(mList.get(i));
		}
	}
}
