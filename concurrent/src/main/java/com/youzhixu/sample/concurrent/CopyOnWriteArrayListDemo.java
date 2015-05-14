package com.youzhixu.sample.concurrent;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author huiman
 * @createAt 2015年5月14日 上午8:33:40
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class CopyOnWriteArrayListDemo {
	public static void main(String[] args) {
		String[] blackList = new String[] {"约炮", "鸡汤", "奋斗"};
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(blackList);
		Iterator<String> iterator = list.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
			iterator.remove();
		}
	}
}
