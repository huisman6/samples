package com.youzhixu.sample.algorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huisman
 * @createAt 2015年6月2日 上午10:04:19
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class SundayDemo {

	public static void main(String[] args) {
		 String[] texts =
		 new String[] {"我似懂非懂所发生的斯蒂芬第三方的手斯蒂芬", "我们是 我是地方的说法多少似懂非懂是", "我是一斯蒂芬额头如何规范法官豆腐干个猪",
		 "我似懂非懂是地方法是一个猪什么", "我是一个猪什么", "我是一个猪", "我是一个猪我是一个猪吗", "我是一个斯蒂芬盛大对方猪个一猪"};

		 String[] patterns =
		 new String[] {"懂所发生的斯蒂发", "方的说法多少似", "我是官豆腐", "什么", "  ", "个已", "猪", "一个猪"};

		long startAt = System.currentTimeMillis();
//		sundaySerarch("我似懂非懂所发生的斯蒂芬第三方的手斯蒂芬", "懂所发生的斯蒂发");
		 for (int i = 0; i < patterns.length; i++) {
		 sundaySerarch(texts[i], patterns[i]);
		 }
		long endAt = System.currentTimeMillis();
		System.out.println("耗时=======》" + (endAt - startAt));

	}

	/**
	 * 统计pattern每个字符出现的位置 -1为没有出现在模式字符串中 <br>
	 * 
	 * @since: 1.0.0
	 * @param pattern
	 * @return
	 */
	private static Map<Character, Integer> calculateCharsTable(String pattern) {
		Map<Character, Integer> badTables = new HashMap<Character, Integer>();
		// 从右到左遍历，也可以从左到右（0，plen-1)，只不过右边重复的字符会覆盖之前的index
		for (int j = pattern.length() - 1; j >= 0; j--) {
			if (!badTables.containsKey(pattern.charAt(j))) {
				// 模式字符串最右边出现的位置
				badTables.put(pattern.charAt(j), j);
			}
		}
		return badTables;
	}

	/**
	 * sunday 算法
	 * 
	 * @since: 1.0.0
	 * @param text
	 * @param pattern
	 */
	private static void sundaySerarch(String text, String pattern) {
		if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
			return;
		}
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen < plen) {
			// return -1;
			return;
		}
		int maxCount = tlen - plen;
		Map<Character, Integer> badCharsTable = calculateCharsTable(pattern);

		int i = 0;
		while (i <= maxCount) {
			int j = 0;
			//子串比较
			while ( j< plen && text.charAt(i + j) == pattern.charAt(j)){
				j++;
			}
			
			//全部匹配
			if (j == plen) {
				System.out.println(text + "=================>found:" + pattern + ",i=" + i);
				return;
			}
			//有字符不匹配
			i += plen;
			if (i < tlen) {
				// 从下一个字符（bad char）处查找出现的位置
				Integer badCharFound = badCharsTable.get(text.charAt(i));
				if (badCharFound == null) {
					// 没有在模式串中找到
					badCharFound = -1;
				}
				i -= badCharFound;
			}
		}
		// not found
		// return -1;
		System.out.println(text + "=================>not found:" + pattern);
	}

}
