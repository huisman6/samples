package com.youzhixu.sample.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *
 * </p>
 * 
 * @author liuhui
 * @createAt 2015年5月12日 下午2:04:20
 * @since 1.0.0
 * @Copyright (c) 2015, Lianjia Group All Rights Reserved.
 */

public class CountDownLatchSemo {
	public static void main(String[] args) {
		// 100 qps
		int qps = 100;
		final CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < qps; i++) {
			Thread t = new Thread("qps-" + i) {
				@Override
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// 请求业务接口代码
					System.out.println("===============>begin query");
				}
			};
			t.start();
		}
		System.out.println("=====================> Let's Go");
		latch.countDown();
	}
}
