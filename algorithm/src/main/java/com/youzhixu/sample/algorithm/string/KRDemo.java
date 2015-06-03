package com.youzhixu.sample.algorithm.string;


/**
 * <p>
 * KR 算法
 * </p>
 * 
 * @author huisman
 * @createAt 2015年6月3日 上午9:51:34
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class KRDemo {

	public static void main(String[] args) {
		// String[] texts =
		// new String[] {"我似懂非懂所发生的斯蒂芬第三方的手斯蒂芬", "我们是 我是地方的说法多少似懂非懂是", "我是一斯蒂芬额头如何规范法官豆腐干个猪",
		// "我似懂非懂是地方法是一个猪什么", "我是一个猪什么", "我是一个猪", "我是一个猪我是一个猪吗", "我是一个斯蒂芬盛大对方猪个一猪"};
		//
		// String[] patterns =
		// new String[] {"懂所发生的斯蒂发", "方的说法多少似", "我是官豆腐", "什么", "  ", "个已", "猪", "一个猪"};

		long startAt = System.currentTimeMillis();
		krSearch("我们是 我是地方的说法多少似懂非懂是", "方的说法多少似");

		// for (int i = 0; i < patterns.length; i++) {
		// krSearch(texts[i], patterns[i]);
		// }
		long endAt = System.currentTimeMillis();
		System.out.println("耗时=======》" + (endAt - startAt));
	}

	/**
	 * rehash 子串 rehash(a,b,h)= ((h-a*(2^m-1))*2+b) mod q <br>
	 * 2^(m-1) 为某个字符的系数,h为当前的子串hash
	 * 
	 * @since: 1.0.0
	 * @param firstChar 子串首字符
	 * @param nextChar 子串下一个字符
	 * @param powerOfN 2^(plen-1)
	 * @param currentSubHash 当前子串的hash值
	 * @return 返回下一个子串的hash值
	 */
	private static int rehash(char firstChar, char nextChar, int powerOfN, int currentSubHash) {
		return (currentSubHash - firstChar * powerOfN) << 1 + nextChar;
	}

	private static void krSearch(String text, String pattern) {
		if (text == null || text.isEmpty() || pattern == null || pattern.isEmpty()) {
			return;
		}
		int tlen = text.length();
		int plen = pattern.length();
		int maxCount = tlen - plen;
		if (maxCount < 0) {
			// return -1;
			return;
		}

		int i, j;
		// 2^(plen-1),max 2^31
		int powerOfN;
		for (powerOfN = i = 1; i < plen; ++i) {
			powerOfN = (powerOfN << 1);
		}


		int hpattern, /* 模式串的hash* */
		htext;/* 子串的hash* */

		// ∑ =m[i]*2^(i-1)....;
		for (hpattern = htext = i = 0; i < plen; ++i) {
			hpattern = ((hpattern << 1) + pattern.charAt(i));
			htext = ((htext << 1) + text.charAt(i));
		}

		i = 0;
		while (i <= maxCount) {
			if (hpattern == htext) {
				// 如果子串和模式串的hash相同，开始逐字符比较
				j = 0;
				while (j < plen && text.charAt(i + j) == pattern.charAt(j)) {
					j++;
				}
				if (j == plen) {
					System.out.println(text + "=================>found:" + pattern + ",i=" + i);
					return;
				}
			}
			// hash不同，下一位
			int nextCharIndex = i + plen;
			if (nextCharIndex < tlen) {
				htext = rehash(text.charAt(i), text.charAt(i + plen), powerOfN, htext);
				i++;
			} else {
				// not found
				break;
			}
		}

		// not found
		// return -1;
		System.out.println(text + "=================>not found:" + pattern);
	}
}
