package shitcode;

/**
 * Created by huangsheng.hs on 2015/1/18.
 */

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CLHLock {
	public static class CLHNode {
		private volatile boolean isLocked = true;
	}

	@SuppressWarnings("unused")
	private volatile CLHNode tail;
	private static final ThreadLocal<CLHNode> ownNode = new ThreadLocal<CLHNode>();
	private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> updater = AtomicReferenceFieldUpdater
			.newUpdater(CLHLock.class, CLHNode.class, "tail");

	public void lock() {
		CLHNode node = new CLHNode();
		ownNode.set(node);
		CLHNode preNode = updater.getAndSet(this, node);
		if (preNode != null) {
			while (preNode.isLocked) {
			}
			ownNode.set(node); // it's necessary to put node back to ThreadLocal
		}
	}

	public void unlock() {
		CLHNode node = ownNode.get();
		if (!updater.compareAndSet(this, node, null)) {
			node.isLocked = false;
		}
	}

	public static void main(String[] args) {
		final CLHLock clhLock = new CLHLock();

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread1 try to lock");
				clhLock.lock();
				System.out.println("Thread1 get the lock");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				clhLock.unlock();
				System.out.println(" Thread1 unlock");
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread2 try to lock");
				clhLock.lock();
				System.out.println("Thread2 get the lock");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				clhLock.unlock();
				System.out.println(" Thread2 unlock");
			}
		});

		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread3 try to lock");
				clhLock.lock();
				System.out.println("Thread3 get the lock");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				clhLock.unlock();
				System.out.println(" Thread3 unlock");
			}
		});
		thread1.start();
		thread2.start();
		thread3.start();
	}
}
