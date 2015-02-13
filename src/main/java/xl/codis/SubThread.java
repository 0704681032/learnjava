package xl.codis;

public class SubThread extends Thread {
	private Object lock;
	private int limit;
	private int num;

	public SubThread(Object lock, int limit) {
		this.lock = lock;
		this.limit = limit;
	}

	@Override
	public void run() {
		while (true) {
			if (this.num < limit) {
				this.num++;
			} else {
				break;
			}
			for (int i = 0; i < 10; i++) {
				System.out.println("SubThread:" + i);
			}
			synchronized (lock) {
				lock.notify();
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Sub end.");
	}
}
