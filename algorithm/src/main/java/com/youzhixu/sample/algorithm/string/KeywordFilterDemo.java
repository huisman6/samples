package com.youzhixu.sample.algorithm.string;


/**
 * <p>
 * 关键词过滤
 * </p>
 * 
 * @author huisman
 * @createAt 2015年6月1日 上午11:47:01
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */
public class KeywordFilterDemo {
	public static void main(String[] args) {
		Character c = 0x4DFF;

		System.out.println("==========>" + c);
		long startAt = System.currentTimeMillis();
		String text = "sdfdsfsdfdsf";
		String[] sensitiveWords = new String[] {"sd", "sf"};
		simpleFilter(text, sensitiveWords);
		long endAt = System.currentTimeMillis();
		System.out.println("处理耗时        ==============>" + (endAt - startAt));
	}


	/**
	 * <p>
	 * 关键词过滤
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param text
	 * @param sensitiveWords
	 */
	private static void simpleFilter(String text, String[] sensitiveWords) {
		StringBuilder stext = new StringBuilder(text);
		System.out.println("before replace===========================>>" + text);
		for (String key : sensitiveWords) {
			int start = -1;
			if ((start = stext.indexOf(key)) > -1) {
				stext.replace(start, start + key.length(), repeatChar('*', key.length()));
				System.out.println("after replace==============>>" + text);
			}
		}

	}

	private static String repeatChar(Character ch, int count) {
		StringBuilder sb = new StringBuilder(count);
		for (int i = 0; i < count; i++) {
			sb.append(ch);
		}
		return sb.toString();
	}
}
