package com.youzhixu.sample.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * @author huisman
 * @createAt 2015年5月13日 下午3:45:55
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class PhaserDemo {
	public static void main(String[] args) {
		useAsCountDownLatch();
	}

	private static void useAsCountDownLatch() {
		int qps = 6;
		final Phaser phaser = new Phaser(1);
		// 业务接口
		List<Runnable> tasks = new ArrayList<Runnable>(qps);
		for (int i = 0; i < 6; i++) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					System.out.println("==============>>> request coming !");
				}
			});
		}
		for (final Runnable task : tasks) {
			// 动态注册一个线程，parties 计数加1
			phaser.register();
			new Thread() {
				@Override
				public void run() {
					// // 等待所有线程创建完毕,parties 计数减1
					phaser.arriveAndAwaitAdvance();
					task.run();
				}
			}.start();
		}
		System.out.println("===========> Let's Go! total parties=" + phaser.getRegisteredParties());
		// 让所有等待的线程开始运行，至此所有parties arrive..
		phaser.arriveAndDeregister();
	}
}
