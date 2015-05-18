package com.youzhixu.sample.concurrent;

import java.util.concurrent.SynchronousQueue;

/**
 * @author huisman
 * @createAt 2015年5月18日 上午8:51:00
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class SynchronousQueueDemo {

	public static void main(String[] args) {
		SynchronousQueue<Integer> queue = new SynchronousQueue<>(true);
	}
}
