package xl.codis;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MyMainThread extends Thread {
	
	
	private CyclicBarrier cb1 ;
	
	private CyclicBarrier cb2 ;


	public MyMainThread(CyclicBarrier cb1,CyclicBarrier cb2) {
		this.cb1 = cb1;
		this.cb2 = cb2;
	}

	public static void main(String[] args) {
		
		CyclicBarrier cb1 = new CyclicBarrier(2) ;
		
		CyclicBarrier cb2 = new CyclicBarrier(2) ;

		MySubThread sub = new MySubThread(cb1, cb2);
		sub.start();

		MyMainThread thread = new MyMainThread(cb1, cb2);
		thread.start();

	}

	public void run() {
		int i = 0 ;
		while ( i < 50*100 ) {
			if ( i%100 ==0) {
				try {
					cb1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("main:"+(++i));
			
			if ( i%100 == 0 ) {
				try {
					cb2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Main end.");
	}

}
