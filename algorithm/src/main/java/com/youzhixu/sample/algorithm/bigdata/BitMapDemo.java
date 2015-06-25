package com.youzhixu.sample.algorithm.bigdata;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author huisman
 * @createAt 2015年6月25日 上午9:46:03
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class BitMapDemo {

	public static void main(String[] args) {
		int maxSize = 10;
		BitMap bm = new BitMap(maxSize);

		// 输入数据
		int[] data = new int[] {3, 0, 7, 4};
		// 首先读取数据，设置对应位=1
		for (int i = 0; i < data.length; i++) {
			bm.set(data[i]);
		}

		// 遍历所有位，从低位到高位
		int size = bm.size();
		for (int i = 0, j = 0; i < size; i++) {
			if (bm.get(i)) {
				data[j++] = i;
				System.out.println("升序输出======>" + i);
			}
		}
		System.out.println("由低位到高位输出===========>" + Arrays.toString(data));
	}

	static class BitMap implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * 一个long类型 字节64位=2^6
		 */
		private static final int LONG_WORD = 6;

		/**
		 * 一个long类型可以表示多少位= 64位
		 */
		private static final int BITS_PER_LONG = 1 << LONG_WORD;
		private long[] buckets;

		public BitMap(int bits) {
			// bits 位数，是能表示元素的个数，1bit的编号(索引）可以表示一个数值，位数也是BitMap所能表示的元素的最大值
			// 当然，BitMap可以在设置或清空指定位时自动扩展位数，以表示更大的元素值，可参考 java.util.BitSet
			if (bits < 0) {
				throw new IllegalArgumentException("bits < 0");
			}
			// 根据总位数来初始化桶
			initBucket(bits);
		}

		private void initBucket(int bits) {
			// 一个long可以表示64位，所以，共需要 （bits/64）+1 个long，可以使用右移加速计算，即右移一次除以2，
			// 则bits/64 +1= （bits >> 6）+1

			// 因为BitMap 元素值从0开始，可以表示的元素值为[0,Integer.MAX_VALUE]，所以bits要减一
			int arrayLen = ((bits - 1) >> LONG_WORD) + 1;

			// 初始化时，数据默认值为0，符号位也为0，所有位都为0
			buckets = new long[arrayLen];
		}

		private boolean get(int bitIndex) {
			// 获取指定位（指定元素值）,指定位=1，返回true,指定位=0，返回false
			// bit所表示的元素从0开始
			if (bitIndex < 0) {
				throw new IllegalArgumentException("bits < 0");
			}
			// 首先计算 bitIndex对应数组的那个Long = bitIndex /64
			int index = bitIndex >> LONG_WORD;
			long bucket = buckets[index];

			// 获取bucket某一位的值
			// 一般来说，bitIndex % 64取余，即是我们想要的指定位
			// return 0 != (bucket & (1 << (bitIndex%64)));
			// 但也可以换种思路，使用左移，正负数左移都会右边补0，
			// 而Java 语言规范指出，左移时，实际移动位数为：左移位数 % 类型所表示的位数。
			// 比如 对long类型来说，实际移动位数=左移位数%64,而int类型，实际移动位数=左移位数%32
			return 0 != (bucket & (1L << bitIndex));
		}

		private void set(int bitIndex) {
			// 设置指定位，将元素值对应的位设置为1
			// bit所表示的元素从0开始
			if (bitIndex < 0) {
				throw new IllegalArgumentException("bits < 0");
			}
			// 首先计算 bitIndex对应数组的那个Long = bitIndex /64
			int index = bitIndex >> LONG_WORD;
			long bucket = buckets[index];

			// 设置bucket某一位的值为1，|（或）常用来值1，&常用来清0，^(异或)常用来置反(1变0，0变1）
			bucket |= (1L << bitIndex);
			// 为了清晰起见，好多步骤拆开了，可以合并
			buckets[index] = bucket;
		}


		private void clear(int bitIndex) {
			// 将指定位 置为0，将元素值对应的位 置为0，类似删除，说明此bit位没有元素对应
			// bit所表示的元素从0开始
			if (bitIndex < 0) {
				throw new IllegalArgumentException("bits < 0");
			}
			// 首先计算 bitIndex对应数组的那个Long = bitIndex /64
			int index = bitIndex >> LONG_WORD;
			long bucket = buckets[index];

			// 设置bucket某一位的值为0，|（或）常用来置1，&常用来清0，^(异或)常用来置反(1变0，0变1）
			// 首先将目标位 置为1，例如：1000 0000，然后，求反，0111 1111，这样只有目标位为0，其余位为1，不影响
			bucket &= (~(1L << bitIndex));
			buckets[index] = bucket;
		}

		private int size() {
			// 此BitMap所能表示的最大数值。
			return buckets.length * BITS_PER_LONG;
		}
	}
}
