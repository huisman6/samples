package com.youzhixu.sample.concurrent;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author huisman
 * @createAt 2015年5月14日 下午1:24:11
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class ArrayBlockingQueueDemo {
	private static final int MAX_ITMES = 20;
	private static boolean shutdown = false;

	public static void main(String[] args) {

		ArrayBlockingQueue<Integer> arrayQueue = new ArrayBlockingQueue<>(MAX_ITMES);
		final Storage storage = new Storage(arrayQueue);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		// 生产者
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (!shutdown) {
						new Producer(storage).produce(2);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		});

		// 消费者
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (!shutdown) {
						new Consumer(storage).consume(4);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		});
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		shutdown = true;
		// 中断
		executorService.shutdown();
		System.out.println("===================> all shut done.");

	}

	private static class Storage {
		private ArrayBlockingQueue<Integer> items;

		public Storage(ArrayBlockingQueue<Integer> items) {
			super();
			this.items = items;
		}

		public void produce(int num) throws InterruptedException {
			if (num <= 0) {
				throw new IllegalArgumentException("num must be >0");
			}
			int[] puts = new int[num];
			for (int i = 0; i < num; i++) {
				int random = ThreadLocalRandom.current().nextInt();
				puts[i] = random;
				items.put(random);
			}
			System.out.println("producer puts:" + Arrays.toString(puts) + ",当前队列大小=====》"
					+ items.size());
		}

		/**
		 * <p>
		 * 消费指定数量的item，如果Item数量不足，则会一直阻塞。
		 * </p>
		 * 
		 * @since: 1.0.0
		 * @param num
		 * @throws InterruptedException
		 */
		public int[] consume(int num) throws InterruptedException {
			if (num <= 0) {
				throw new IllegalArgumentException("num must be >0");
			}
			int[] takes = new int[num];;

			for (int i = 0; i < num; i++) {
				int item = items.take();
				takes[i] = item;
			}
			System.out.println("consumer take:" + Arrays.toString(takes) + ",当前队列大小=====》"
					+ items.size());
			return takes;
		}
	}

	private static class Producer {
		private Storage storage;

		public Producer(Storage storage) {
			super();
			this.storage = storage;
		}

		/**
		 * <p>
		 * 生产指定数量的item
		 * </p>
		 * 
		 * @since: 1.0.0
		 * @param num numer of items will be producing
		 * @throws InterruptedException
		 */
		public void produce(int num) throws InterruptedException {
			storage.produce(num);
		}
	}

	private static class Consumer {
		private Storage storage;

		public Consumer(Storage storage) {
			super();
			this.storage = storage;
		}

		/**
		 * <p>
		 * 消费指定数量的item
		 * </p>
		 * 
		 * @since: 1.0.0
		 * @param num numer of items will be consuming
		 * @throws InterruptedException
		 */
		public void consume(int num) throws InterruptedException {
			storage.consume(num);
		}

	}
}
