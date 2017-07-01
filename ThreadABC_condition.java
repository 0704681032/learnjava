package java8;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadABC_condition {

	public static void main(String[] args) {
		
		final Lock lock = new ReentrantLock();
		Condition conditionA = lock.newCondition();
		Condition conditionB = lock.newCondition();
		Condition conditionC = lock.newCondition();
		
		AtomicReference<String> atomicReference = new AtomicReference<String>("A");
		
		Thread threadA = new Thread(new Worker("A",lock,conditionA,conditionB,atomicReference,"B"));
		Thread threadB = new Thread(new Worker("B",lock,conditionB,conditionC,atomicReference,"C"));
		Thread threadC = new Thread(new Worker("C",lock,conditionC,conditionA,atomicReference,"A"));
		
		
		
//		
		threadA.start();
		threadB.start();
		threadC.start();
		
//		lock.lock();
//		conditionA.signal();
//		lock.unlock();
		
		
		try {
			threadA.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	static class Worker implements Runnable {
		
		String name ;
		Condition pre ;
		Condition next;
		Lock lock;
		AtomicReference<String> atomicReference;		
		String nextName ;
		//Condition co;
		
		
		public Worker(String name, Lock lock,Condition pre, Condition next,AtomicReference<String> atomicReference,String nextName) {
			this.name = name;
			this.lock  = lock;
			this.pre = pre;
			this.next = next;
			this.atomicReference = atomicReference;
			this.nextName = nextName ;
		}


		@Override
		public void run() {
			//boolean first = true ;
			//int i = 0 ;
			while ( true ) {
				
				try {
					//System.out.println(Thread.currentThread().getName()+"=>"+i++);
					lock.lock();
						while (  ! atomicReference.get().equals(name) ) {
							try {
								pre.await();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
					
					System.out.println("name"+this.name);
					
					//first = false ;
					//System.out.println(Thread.currentThread().getName()+"=>here");
					
					atomicReference.set( nextName );
					
					next.signal();

				} finally {
					lock.unlock();
				}
			}
			
		}
	
		
		
		
	}

}
