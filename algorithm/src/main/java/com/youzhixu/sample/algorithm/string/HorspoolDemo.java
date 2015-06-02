package com.youzhixu.sample.algorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huisman
 * @createAt 2015年6月2日 上午10:04:19
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class HorspoolDemo {

	public static void main(String[] args) {
		String[] texts =
				new String[] {"我似懂非懂所发生的斯蒂芬第三方的手斯蒂芬", "我们是 我是地方的说法多少似懂非懂是", "我是一斯蒂芬额头如何规范法官豆腐干个猪",
						"我似懂非懂是地方法是一个猪什么", "我是一个猪什么", "我是一个猪", "我是一个猪我是一个猪吗", "我是一个斯蒂芬盛大对方猪个一猪"};

		String[] patterns =
				new String[] {"懂所发生的斯蒂发", "方的说法多少似", "我是官豆腐", "什么", "  ", "个已", "猪", "一个猪"};

		long startAt = System.currentTimeMillis();
		for (int i = 0; i < patterns.length; i++) {
			horspoolSerarch(texts[i], patterns[i]);
		}
		long endAt = System.currentTimeMillis();
		System.out.println("耗时=======》" + (endAt - startAt));

	}

	/**
	 * <p>
	 * 统计pattern每个字符出现的位置 -1为没有出现在模式字符串中 <br>
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param pattern
	 * @return
	 */
	private static Map<Character, Integer> calculateCharsTable(String pattern) {
		Map<Character, Integer> badTables = new HashMap<Character, Integer>();
		// 跳过pattern最后一个字节
		for (int j = pattern.length() - 2; j >= 0; j--) {
			if (!badTables.containsKey(pattern.charAt(j))) {
				// 模式字符串最右边出现的位置
				badTables.put(pattern.charAt(j), j);
			}
		}
		return badTables;
	}

	/**
	 * <p>
	 * horspool 算法
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param text
	 * @param pattern
	 */
	private static void horspoolSerarch(String text, String pattern) {
		if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
			return;
		}
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen < plen) {
			// return -1;
			return;
		}
		int skip;
		int maxCount = tlen - plen;
		Map<Character, Integer> badCharsTable = calculateCharsTable(pattern);
		for (int i = 0; i <= maxCount; i += skip) {
			skip = 0;
			for (int j = plen - 1; j >= 0; j--) {
				// 有字符不匹配，模式串开始右移
				if (pattern.charAt(j) != text.charAt(i + j)) {
					// 始终从匹配窗口最后一个字符
					Integer badCharFound = badCharsTable.get(text.charAt(i + (plen - 1)));
					if (badCharFound == null) {
						// 没有在模式串中找到
						badCharFound = -1;
					}
					// 无论如何，每次右移的位置都是最后一个字符所在的位置-尾字符在模式串中出现的位置
					skip = Math.max(1, (plen - 1) - badCharFound);
					break;
				}
			}
			if (skip == 0) {
				System.out.println(text + "=================>found:" + pattern + ",i=" + i);
				return;
			}

		}
		// not found
		// return -1;
		System.out.println(text + "=================>not found:" + pattern);
	}

}
