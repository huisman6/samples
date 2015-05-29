package com.youzhixu.sample.algorithm.string;

import java.util.Arrays;

/**
 * @author huiman
 * @createAt 2015年5月22日 上午9:01:54
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */
public class KMPDemo {
	public static void main(String[] args) {
		System.out.println("next==========>" + Arrays.toString(calculateNext("BCEB")));
		String[] texts =
				new String[] {"我", "我们是 我", "我是一个猪", "我是一个猪什么", "我是一个猪什么", "我是一个猪", "我是一个猪我是一个猪吗",
						"我是一个猪个一猪"};

		String[] patterns = new String[] {"我是", " ", "我是", "什么", "  ", "个已", "猪", "一个猪"};
		long startAt = System.currentTimeMillis();
		for (int i = 0; i < patterns.length; i++) {
			kmpSearch(texts[i], patterns[i]);

			// simpleSearch(texts[i], patterns[i]);
		}
		long endAt = System.currentTimeMillis();
		System.out.println("耗时=======》" + (endAt - startAt));
	}

	private static void kmpSearch(String text, String pattern) {
		if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
			return;
		}
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen < plen) {
			// return -1;
			return;
		}
		int i = 0, j = 0;
		int[] nextArray = calculateNext(pattern);
		while (i < tlen && j < plen) {
			// 如果j=-1,或者当前字符有匹配成功，则将i和j都向后移，比较下一个字符是否相等
			if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
			} else {
				// 遇到未匹配的字符 （j!=-1 && text.charAt(i) != pattern.charAt(j));
				// i不回退到（i-j),i值保持不变;
				// 只是把模式字符串对应的索引j移动一定位置
				j = nextArray[j];
			}
		}
		if (j == plen) {
			System.out.println("text:" + text + ",found ===================>" + pattern);
		} else {
			System.out.println("text:" + text + ",not found ===================>" + pattern);
		}
	}

	private static int[] calculateNext(String pattern) {
		int plen = pattern.length();
		int[] nexts = new int[plen];
		// 初始值
		nexts[0] = -1;
		int k = -1;
		int j = 0;

		while (j < plen - 1) {
			// p[k]表示前缀，p[j]表示后缀
			if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
				++j;
				++k;
				if (pattern.charAt(j) != pattern.charAt(k)) {
					nexts[j] = k; //
				} else {
					// 因为不能出现p[j] = p[ next[j ]]，所以当出现时需要继续递归，k = next[k] = next[next[k]]
					nexts[j] = nexts[k];
				}
			} else {
				k = nexts[k];
			}
		}
		return nexts;
	}


	private static void simpleSearch(String text, String pattern) {
		if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
			return;
		}
		int tlen = text.length();
		int plen = pattern.length();
		if (tlen < plen) {
			// return -1;
			return;
		}

		for (int i = 0; i < tlen; i++) {
			// 遍历扫描text，和pattern比较
			if (text.charAt(i) != pattern.charAt(0)) {
				// 首字母都不匹配，直接继续扫描text下一个字符
				continue;
			}
			// 如果首字母匹配了,扫描pattern接下来的字符序列是否和text[currentIndex,currentIndex+plen)的一样
			int j = 1;
			// i从匹配的首字母向后移,j代表了已匹配的字符数量
			for (i++; j < plen; j++, i++) {
				if (pattern.charAt(j) != text.charAt(i)) {
					// 有一个字符序列不一样,停止扫描pattern剩余的字符，继续外层循环
					break;
				}
			}
			if (j == plen) {
				// return i;
				// 说明匹配
				System.out.println("found index=" + (i - j) + ",text=" + text + ",searched String:"
						+ text.substring(i - j, i));
				return;
			} else {
				// 没有匹配，i要回退到之前的位置，从下一个字符开始新的一轮
				i = i - j;
				System.out.println("text=" + text + ",not found:" + pattern);
			}
		}
		// return -1;
	}
}
