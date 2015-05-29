package com.youzhixu.sample.algorithm.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huisman
 * @createAt 2015年5月29日 上午10:59:47
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class BMDemo {

	public static void main(String[] args) {
		String[] texts =
				new String[] {"我似懂非懂所发生的斯蒂芬第三方的手斯蒂芬", "我们是 我是地方的说法多少似懂非懂是", "我是一斯蒂芬额头如何规范法官豆腐干个猪",
						"我似懂非懂是地方法是一个猪什么", "我是一个猪什么", "我是一个猪", "我是一个猪我是一个猪吗", "我是一个斯蒂芬盛大对方猪个一猪"};

		String[] patterns =
				new String[] {"懂所发生的斯蒂发", "方的说法多少似", "我是官豆腐", "什么", "  ", "个已", "猪", "一个猪"};

		long startAt = System.currentTimeMillis();
		for (int i = 0; i < patterns.length; i++) {
			// bmSearchWithoutGoodSuffix(texts[i], patterns[i]);
			bmSearch(texts[i], patterns[i]);
		}
		long endAt = System.currentTimeMillis();
		System.out.println("耗时=======》" + (endAt - startAt));
	}

	/**
	 * <p>
	 * Boyer-Moore字符串搜索算法 <br>
	 * 没有使用good suffix规则
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param text
	 * @param pattern
	 */
	private static void bmSearchWithoutGoodSuffix(String text, String pattern) {
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
		Map<Character, Integer> badCharsTable = calculateBadCharsTable(pattern);
		for (int i = 0; i <= maxCount; i += skip) {
			skip = 0;
			for (int j = plen - 1; j >= 0; j--) {
				if (pattern.charAt(j) != text.charAt(i + j)) {
					Integer badCharFound = badCharsTable.get(text.charAt(i + j));
					if (badCharFound == null) {
						// 没有在模式串中找到
						badCharFound = -1;
					}
					// 坏字符的位置-坏字符在模式串中的位置
					skip = Math.max(1, j - badCharFound);
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

	/**
	 * <p>
	 * 使用Bad-char和Good-suffix规则的BM字符串搜索
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param text
	 * @param pattern
	 */
	private static void bmSearch(String text, String pattern) {
		if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
			return;
		}
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen < plen) {
			// return -1;
			return;
		}
		Map<Character, Integer> badCharsTable = calculateBadCharsTable(pattern);
		// 好后缀移动的位置
		int[] match = computeGoodSuffixMatch(pattern);
		// Searching
		int i = plen - 1;
		int j = plen - 1;
		// 从尾部开始扫描
		while (i < tlen) {
			if (pattern.charAt(j) == text.charAt(i)) {
				if (j == 0) {
					System.out.println(text + "=================>found:" + pattern + ",i=" + i);
					return;
					// return i;
				}
				j--;
				i--;
			} else {
				// 从好后缀和坏字符规则中选择右移次数最多的字符串
				Integer badCharOffset = badCharsTable.get(text.charAt(i));
				if (badCharOffset == null) {
					badCharOffset = -1;
				}
				i += plen - j - 1 + Math.max(j - badCharOffset, match[j]);
				j = plen - 1;
			}
		}
		// not found
		// return -1;
		System.out.println(text + "=================>not found:" + pattern);
	}

	/**
	 * <p>
	 * 统计pattern坏字符出现的位置 -1为没有出现在模式字符串中 <br>
	 * 我们计算遇到坏字符时模式字符串右移的位数= 坏字符的位置-坏字符在pattern中出现的位置 <br>
	 * 如果坏字符没有包含在pattern中，那么坏字符上一次出现的位置就为-1 <br>
	 * 如果坏字符包含在pattern中，那么我们可以计算出坏字符对应的索引（从0开始）；
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param pattern
	 * @return
	 */
	private static Map<Character, Integer> calculateBadCharsTable(String pattern) {
		Map<Character, Integer> badTables = new HashMap<Character, Integer>();
		for (int j = pattern.length() - 1; j >= 0; j--) {
			if (!badTables.containsKey(pattern.charAt(j))) {
				// 模式字符串最右边出现的位置
				badTables.put(pattern.charAt(j), j);
			}
		}
		return badTables;
	}

	/**
	 * Computes the values of suffix, which is an auxiliary array, backwards version of the KMP
	 * failure function.
	 * 
	 * suffix[i] = the smallest j > i s.t. p[j..m-1] is a prefix of p[i..m-1], if there is no such
	 * j, suffix[i] = m, i.e.
	 * 
	 * p[suffix[i]..m-1] is the longest prefix of p[i..m-1], if suffix[i] < m.
	 */
	private static int[] computeSuffix(String pattern) {
		int len = pattern.length();
		int[] suffix = new int[len];
		// 最后一个字符
		suffix[suffix.length - 1] = len;
		// 最后一个字符
		int j = suffix.length - 1;
		// i从倒数第二个字符开始
		for (int i = suffix.length - 2; i >= 0; i--) {
			while (j < suffix.length - 1 && pattern.charAt(j) != pattern.charAt(i)) {
				j = suffix[j + 1] - 1;
			}
			if (pattern.charAt(j) == pattern.charAt(i)) {
				j--;
			}
			suffix[i] = j + 1;
		}
		return suffix;
	}


	/**
	 * Computes the function match and stores its values in the array match. match(j) = min{ s | 0 <
	 * s <= j && p[j-s]!=p[j] && p[j-s+1]..p[m-s-1] is suffix of p[j+1]..p[m-1] }, if such s exists,
	 * else min{ s | j+1 <= s <= m && p[0]..p[m-s-1] is suffix of p[j+1]..p[m-1] }, if such s
	 * exists, m, otherwise, where p is the pattern and m is its length. 计算
	 * 
	 * @since: 1.0.0
	 * @param pattern
	 * @return
	 */
	private static int[] computeGoodSuffixMatch(String pattern) {
		// 计算好后缀移动的位置
		/* Phase 1 */
		int plen = pattern.length();
		int[] match = new int[plen];
		Arrays.fill(match, plen);

		int[] suffix = computeSuffix(pattern); // O(m)

		/* Phase 2 */
		// Uses an auxiliary array, backwards version of the KMP failure function.
		// suffix[i] = the smallest j > i s.t. p[j..m-1] is a prefix of p[i..m-1],
		// if there is no such j, suffix[i] = m

		// Compute the smallest shift s, such that 0 < s <= j and
		// p[j-s]!=p[j] and p[j-s+1..m-s-1] is suffix of p[j+1..m-1] or j == m-1},
		// if such s exists,
		for (int i = 0; i < match.length - 1; i++) {
			int j = suffix[i + 1] - 1; // suffix[i+1] <= suffix[i] + 1
			if (suffix[i] > j) { // therefore pattern[i] != pattern[j]
				match[j] = j - i;
			} else {// j == suffix[i]
				match[j] = Math.min(j - i + match[i], match[j]);
			}
		}

		/* Phase 3 */
		// Uses the suffix array to compute each shift s such that
		// p[0..m-s-1] is a suffix of p[j+1..m-1] with j < s < m
		// and stores the minimum of this shift and the previously computed one.
		if (suffix[0] < pattern.length()) {
			for (int j = suffix[0] - 1; j >= 0; j--) {
				if (suffix[0] < match[j]) {
					match[j] = suffix[0];
				}
			}
			int j = suffix[0];
			for (int k = suffix[j]; k < pattern.length(); k = suffix[k]) {
				while (j < k) {
					if (match[j] > k) match[j] = k;
					j++;
				}
			}
		}
		return match;
	}

}
