package com.youzhixu.sample.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author huiman
 * @createAt 2015年5月14日 下午4:20:42
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */
public class LinkedBlockingQueueDemo {
	public static void main(String[] args) {
		int queueSize = 10;
		LinkedBlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<>(queueSize);
		new Thread(new Consumer(linkedQueue)).start();
		new Thread(new Producer(linkedQueue)).start();
	}

	private static class Producer implements Runnable {
		private LinkedBlockingQueue<Integer> linkedBlockingQueue;

		public Producer(LinkedBlockingQueue<Integer> linkedBlockingQueue) {
			super();
			this.linkedBlockingQueue = linkedBlockingQueue;
		}

		@Override
		public void run() {
			try {
				int i = 1;
				while (i++ <= 5) {
					int item = ThreadLocalRandom.current().nextInt(5000);
					linkedBlockingQueue.put(item);
					System.out.println("item added:" + item);
					Thread.sleep(5);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class Consumer implements Runnable {
		private LinkedBlockingQueue<Integer> linkedBlockingQueue;

		public Consumer(LinkedBlockingQueue<Integer> linkedBlockingQueue) {
			super();
			this.linkedBlockingQueue = linkedBlockingQueue;
		}

		@Override
		public void run() {
			try {
				int i = 1;
				while (i++ <= 5) {
					int item = linkedBlockingQueue.take();
					System.out.println("item got:" + item);
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
