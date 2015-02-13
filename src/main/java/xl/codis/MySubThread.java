package xl.codis;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MySubThread extends Thread {
	
	private CyclicBarrier cb1;

	private CyclicBarrier cb2;

	public MySubThread(CyclicBarrier cb1,CyclicBarrier cb2) {
		this.cb1 = cb1;
		this.cb2 = cb2;
	}

	@Override
	public void run() {
		int i = 0 ;
		while ( i++ < 50*10 ) {
			System.out.println("sub:"+i);
			if ( i%10 == 0 ) {
				try {
					cb1.await();
					cb2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("Sub end.");
	}
}
