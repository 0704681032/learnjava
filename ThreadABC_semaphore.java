package java8;

import java.util.concurrent.Semaphore;

public class ThreadABC_semaphore {

	public static void main(String[] args) {
				
		Semaphore semaphoreA =  new Semaphore(0);
		Semaphore semaphoreB =  new Semaphore(0);
		Semaphore semaphoreC =  new Semaphore(0);
		
		Thread threadA = new Thread(new Worker1("A",semaphoreA,semaphoreB));
		
		Thread threadB = new Thread(new Worker1("B",semaphoreB,semaphoreC));

		Thread threadC = new Thread(new Worker1("C",semaphoreC,semaphoreA));
		
//		try {
//			semaphoreA.acquire();
//			semaphoreB.acquire();
//			semaphoreC.acquire();
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		
		
		threadA.start();
		threadB.start();
		threadC.start();
		
		semaphoreA.release();
		try {
			threadA.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class Worker1 implements Runnable {
		
		String name ;
		Semaphore pre ;
		Semaphore next;
		
		public Worker1(String name, Semaphore pre, Semaphore next) {
			this.name = name;
			this.pre = pre;
			this.next = next;
		}


		@Override
		public void run() {
			while ( true ) {
				try {
					pre.acquire();;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(this.name+"");
				next.release();
			}
			
		}
		
	}
	


}
