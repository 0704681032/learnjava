package xl.codis;

public class MainThread extends Thread {
	private Object lock;
	private int num;
	private int limit;

	public MainThread(Object lock, int limit) {
		this.lock = lock;
		this.limit = limit;
	}

	public static void main(String[] args) {
		Object lock = new Object();

		SubThread sub = new SubThread(lock, 50);
		sub.start();

		MainThread thread = new MainThread(lock, 50);
		thread.start();

	}

	public void run() {
		while (true) {
			if (this.num < limit) {
				this.num++;
			} else {
				synchronized (lock) {
					lock.notify(); // 通知子线程结束
					System.out.println("通知子线程结束");
				}
				break;
			}
			synchronized (lock) {
				lock.notify();
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < 100; i++) {
				System.out.println("MainThread:" + i);
			}
		}
		System.out.println("Main end.");
	}

}
