package com.youzhixu.sample.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author huisman
 * @createAt 2015年5月13日 上午8:16:11
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class CyclicBarrierDemo {

	private static class PartTask implements Runnable {
		private CyclicBarrier barrier;

		public PartTask(CyclicBarrier barrier) {
			this.barrier = barrier;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				System.out.println(Thread.currentThread().getName() + " 正在公共屏障点等待.....");
				barrier.await();
				System.out.println(Thread.currentThread().getName() + " 已正常离开屏障点......");
			} catch (InterruptedException | BrokenBarrierException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		final int parties = 5;
		final CyclicBarrier barrier = new CyclicBarrier(parties, new Runnable() {
			@Override
			public void run() {
				System.out.println("Ha ha, all done , my turn!");
			}
		});
		for (int i = 0; i < parties; i++) {
			Thread t = new Thread(new PartTask(barrier), "parties " + (i + 1));
			t.start();
		}

	}

}
