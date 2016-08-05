package java8;

import java.util.Stack;

/**
 * 阿里巴巴面试题  两个栈实现队列
 * @author jyy
 * TODO 如何设计好的测试用例
 * @param <T>
 */
public class MyQueue<T> {
	private static final String ENQUEUE = "enqueue";
	private static final String DEQUEUE = "dequeue";
	
	private Stack<T> stack = new Stack<T>();
	private Stack<T> otherStack =  new Stack<T>();
	private Stack<T> currentStack = stack;
	private String lastOperation = "";
	
	public void enqueue(T t){
		if ( ENQUEUE.equals(lastOperation) || "".equals(lastOperation) ) {
			currentStack.push(t);
		} else if ( DEQUEUE.equals(lastOperation) ) {
			transferToAnother(t);
		}
		lastOperation = ENQUEUE;
	}
	private void transferToAnother(T t) {
		Stack<T> other = currentStack == stack ? otherStack : stack;
		while ( !currentStack.isEmpty() ) {
			other.push(currentStack.pop());
		}
		if ( t != null ) {
			other.push(t);
		}
		currentStack = other; 	
	}

	public T dequeue(){
		if ( DEQUEUE.equals(lastOperation) ) { //路径一样,完全可以去掉,放在这里体现思路
			//t =  currentStack.pop();
		} else if ( ENQUEUE.equals(lastOperation) ) {
			transferToAnother(null);
		}
		lastOperation = DEQUEUE;
		return currentStack.pop();
	}

	public static void main(String[] args) {		
		MyQueue<String> queue = new MyQueue<String>();
		queue.enqueue("A");
		queue.enqueue("B");
		queue.enqueue("C");
		System.out.println(queue.dequeue());
		queue.enqueue("D");
		queue.enqueue("E");
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());
		queue.enqueue("G");
		System.out.println(queue.dequeue());
		queue.enqueue("H");
		System.out.println(queue.dequeue());
		System.out.println(queue.dequeue());

		
	}

}
