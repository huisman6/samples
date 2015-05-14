package com.youzhixu.sample.concurrent;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author huisman
 * @createAt 2015年5月14日 上午9:51:28
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class CopyOnWriteArraySetDemo {

	public static void main(String[] args) {
		String[] words =
				new String[] {"who", "are", "you", "you", "are", "sick", "i", "hate", "you", "how",
						"you", "doing", "get", "out", "shut", "up"};
		CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
		set.addAll(Arrays.asList(words));

		Iterator<String> iterator = set.iterator();
		int len = 0;
		while (iterator.hasNext()) {
			len++;
			printPrefix("=", len);
			System.out.println(">" + iterator.next());
		}

		System.out.println("words size:" + words.length + " ， CopyOnWriteArraySet  size:"
				+ set.size());
	}

	private static void printPrefix(String prefix, int len) {
		for (int i = 0; i < len; i++) {
			System.out.print(prefix);
		}
	}

}
