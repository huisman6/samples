package com.youzhixu.sample.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * <p>
 * 信号量测试
 * </p>
 * 
 * @author liuhui
 * @createAt 2015年5月12日 下午1:12:32
 * @since 1.0.0
 * @Copyright (c) 2015, Lianjia Group All Rights Reserved.
 */

public class SemaphoreDemo {
	public static void main(String[] args) {
		int threadCount = 50;
		ExecutorService threadPool = Executors.newFixedThreadPool(threadCount);
		final int permits = 10;
		// 10个许可
		final Semaphore semaphore = new Semaphore(permits, Boolean.TRUE);

		for (int i = 0; i < threadCount; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						// 可以请求多个许可 semaphore.acquire(2);
						semaphore.acquire(1);
						Thread.sleep(2000);
						System.out.println("===============>>当前线程已获取许可，可用许可数："
								+ semaphore.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						// 可以释放多个许可 semaphore.release(2);
						semaphore.release();
					}
				}
			});
		}

	}
}
