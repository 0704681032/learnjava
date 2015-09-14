package java8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Test1 {
	
	//private static final Vector v = new Vector();
	
	public static void main(String[] args) {
		Semaphore s = new Semaphore(0);
		Semaphore s1 = new Semaphore(0);
		Task task = new Task(new int[]{2,4,6},s,s1);
		Task2 task2 = new Task2(new int[]{1,3,5},s,s1);
		ExecutorService e = Executors.newFixedThreadPool(2);
		e.execute(task);
		e.execute(task2);
		e.shutdown();
//		Thread t = new Thread(task);
//		Thread t1 = new Thread(task2);
//		t.start();
//		t1.start();
//		try {
//			t.join();
//			t1.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	static class Task implements Runnable {
		private  int[] datas ;
		private  Semaphore s;
		private   Semaphore s1;
		public Task(int[] datas, Semaphore s,Semaphore s1) {
			this.datas = datas;
			this.s = s ;
			this.s1 = s1;
		}		
		@Override
		public void run() {
			for(int i : datas) {
				try {
					s1.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(i);
				//v.add(i);
				s.release();
			}
			
		}
		
	}
	
	static class Task2 implements Runnable {
		private  int[] datas ;
		private   Semaphore s;
		private   Semaphore s1;
		public Task2(int[] datas, Semaphore s,Semaphore s1) {
			super();
			this.datas = datas;
			this.s = s ;
			this.s1 = s1 ;
		}		
		@Override
		public void run() {
			for(int i : datas) {
				System.out.println(i);
				//v.add(i);
				s1.release();
				try {
					s.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
