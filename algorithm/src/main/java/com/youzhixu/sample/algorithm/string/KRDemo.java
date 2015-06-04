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
		 String[] texts =
		 new String[] {"我似懂非懂所发生的斯蒂芬第三方的手斯蒂芬", "我们是 我是地方的说法多少似懂非懂是", "我是一斯蒂芬额头如何规范法官豆腐干个猪",
		 "我似懂非懂是地方法是一个猪什么", "我是一个猪什么", "我是一个猪", "我是一个猪我是一个猪吗", "我是一个斯蒂芬盛大对方猪个一猪"};
		
		 String[] patterns =
		 new String[] {"懂所发生的斯蒂发", "方的说法多少似", "我是官豆腐", "什么", "  ", "个已", "猪", "一个猪"};

		long startAt = System.currentTimeMillis();
//		krSearch("654321", "43");

		 for (int i = 0; i < patterns.length; i++) {
			 krSearch(texts[i], patterns[i]);
		 }
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
	private static long rehash(char firstChar, char nextChar, int powerOfN,long prime, long currentSubHash) {
		//类型提升 char转换为long 符号位扩展0,如果是其他类型，可以利用掩码防止符号位扩展
		//((currentSubHash - firstChar * powerOfN) << 1 )+ nextChar&0xFFFFL
		return (((currentSubHash - firstChar * powerOfN) << 1 )+ nextChar )%prime;
		//另外(currentSubHash - firstChar * powerOfN) << 1 + nextChar 是错误的，因为<< 优先级没+号高
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

		// 2^(plen-1),max 2^31
		//32bit，最多支持32个模式串，多了溢出，也可以使用long
		int powerOfR=1 << (plen-1);


		long hpattern, /* 模式串的hash* */
		hsub;/* 子串的hash* */
		hpattern = hsub = 0;
		//64位
		long prime=1099511628211L;
		//32位
		//int prime=16777619;
		// 或者采用BigInteger动态计算
		// BigInteger prime = BigInteger.probablePrime(31, new Random());
        // return prime.longValue();
		
		// ∑ =m[i]*2^(i-1)....;
		//hash(str[0..m-1])=(str[0]*2^(m-1)+str[1]*2^(m-2)+……+str[m-1]*2^0)mod q
		for (int shift=plen-1,i=0; i <plen ; i++,shift--) {
			hpattern += (pattern.charAt(i) << shift) %prime;
			hsub += (text.charAt(i) << shift)%prime;
		}

		int i=0;
		int nextCharIndex=0;
		while (i <= maxCount) {
			if (hpattern == hsub) {
				// 如果子串和模式串的hash相同，开始逐字符精确比较
				int j = 0;
				while (j < plen && text.charAt(i + j) == pattern.charAt(j)) {
					j++;
				}
				if (j == plen) {
					System.out.println(text + "=================>found:" + pattern + ",i=" + i);
					return;
				}
			}
			// hash不同，下一个字串text[i+1,i+plen],检查是否超出字符串长度
			nextCharIndex = i + plen;
			if (nextCharIndex < tlen) {
				hsub = rehash(text.charAt(i), text.charAt(nextCharIndex), powerOfR,prime, hsub);
				i++;
			}else{
				break;
			}
		}

		// not found
		// return -1;
		System.out.println(text + "=================>not found:" + pattern);
	}
}
