package com.youzhixu.sample.concurrent;

import java.util.concurrent.Exchanger;

/**
 * @author huisman
 * @createAt 2015年5月13日 下午1:21:57
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class ExchangerDemo {

	public static void main(String[] args) {
		Exchanger<String> exchanger = new Exchanger<String>();
		Thread t1 = new TaskThread(exchanger, "Joey Tribbiani", "how you doing? ");
		Thread t2 = new TaskThread(exchanger, "pretty girl", "I am lesbian.");
		t1.start();
		t2.start();
	}

	static class TaskThread extends Thread {

		Exchanger<String> exchanger;
		String message;

		TaskThread(Exchanger<String> exchanger, String name, String message) {
			this.exchanger = exchanger;
			this.message = message;
			this.setName(name);
		}

		@Override
		public void run() {
			try {
				System.out.println("before exchange ===============>" + this.getName()
						+ " send message: " + message);
				message = exchanger.exchange(message);
				System.out.println("after exchange================>" + this.getName() + " got："
						+ message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
