package com.youzhixu.sample.algorithm.sort;

import java.util.Arrays;

/**
 * @author huisman
 * @createAt 2015年6月9日 上午8:39:00
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class SortDemo {

	public static void main(String[] args) {
		int[] data = new int[] {-1, 3, 5, 1, 0, -3};
		System.out.println("before sort:" + Arrays.toString(data));
		simpleSelectionSort(data);
		System.out.println("after sort:" + Arrays.toString(data));
	}

	private static int[] simpleSelectionSort(int[] data) {
		int len = data.length;
		int outerLen = len - 1;
		int min, tmp;
		for (int i = 0; i < outerLen; i++) {
			min = i;
			// 比较[i+1,len-1]之间
			for (int j = i + 1; j < len; j++) {
				// 这里可以控制ascent,descent
				if (data[j] < data[min]) {
					min = j;
				}
			}
			if (min != i) {
				// 交换两者
				tmp = data[i];
				data[i] = data[min];
				data[min] = tmp;
			}
		}

		return data;
	}
}
