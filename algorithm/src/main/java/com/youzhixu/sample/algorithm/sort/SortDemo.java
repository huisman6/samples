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
		// simpleSelectionSort(data);
		// simpleSelectionSortV2(data);
		// bubleSort(data);
		// bubbleSortV2(data);
		// insertionSort(data);
		// insertionSortV2(data);
		// shellSort(data);
		shellSortV2(data);
		System.out.println("after sort:" + Arrays.toString(data));
	}

	private static int[] simpleSelectionSort(int[] data) {
		int len = data.length;
		// 外层循环次数
		int outerLen = len - 1;
		int min, tmp;
		for (int i = 0; i < outerLen; i++) {
			min = i;
			// 从data[i+1,len-1]之间选择最小的元素放在data[i]
			for (int j = i + 1; j < len; j++) {
				// 这里可以控制ascent,descent
				if (data[j] < data[min]) {
					min = j;
				}
			}
			// 如果data[i]和data[i+1,len-1]中最小的元素相同，则不交换
			if (min != i) {
				// 交换两者
				tmp = data[i];
				data[i] = data[min];
				data[min] = tmp;
			}
		}

		return data;
	}

	private static int[] simpleSelectionSortV2(int[] data) {
		int len = data.length;
		// 外层循环次数，不超过n/2趟
		int outerLen = len / 2;
		int min, max, tmp;
		// i代表已经排号序的元素的个数
		for (int i = 0; i < outerLen; i++) {
			// 刚开始，data[0]即是最小的，也是最大的。
			// 我们按照升序 小 -> 大的顺序排序
			min = i;
			max = i;

			// 从data[i+1,len-1]之间选择最小的元素放在data[i]
			for (int j = i + 1; j < len - i; j++) {
				// 这里可以控制ascent,descent
				if (data[j] > data[max]) {
					// 如果data[j]的元素大于data[max]，因为data[max]=data[min]，
					// 那么data[j]肯定大于data[min]，就不用执行下面的逻辑了
					max = j;
					continue;
				}
				if (data[j] < data[min]) {
					min = j;
				}
			}

			// 先挪小的，把左边的排好序
			if (min != i) {
				// 交换两者（前半部分交换）
				tmp = data[i];
				data[i] = data[min];
				data[min] = tmp;
			}

			// 再挪大的（把右边的排好序）
			// 将data[max]和data[len-1-i]交换
			if (max != len - 1 - i) {
				if (max == i) {
					// 因为data[i]其实被用来放置data[min]的，所以，data[i]肯定会被交换到data[min]的位置
					// 也就是说data[max]已经被交换到data[min]的位置上
					tmp = data[min];
					data[min] = data[len - 1 - i];
					data[len - 1 - i] = tmp;
				} else {
					// 说明data[min]和data[max]没有重合，直接和data[len-1-i]交换
					tmp = data[max];
					data[max] = data[len - 1 - i];
					data[len - 1 - i] = tmp;
				}

			}
			System.out.println("第" + (i + 1) + "趟===>" + Arrays.toString(data));
		}

		return data;
	}

	private static int[] bubbleSort(int data[]) {
		int len = data.length;
		// 因为是两个元素交换位置，所以，外层只需要到len-2(即 < len-1)处。
		int outLen = len - 1;
		int tmp;
		// i也可以理解为已经排好序的元素个数
		for (int i = 0; i < outLen; i++) {
			// 内循环的次数为：未排好序元素的个数
			for (int j = 0; j < outLen - i; j++) {
				if (data[j] > data[j + 1]) {
					// 升序，数字大的冒泡到右端
					// 相邻元素不相等，则交换
					tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;
				}
			}
		}
		return data;
	}


	private static int[] bubbleSortV2(int data[]) {
		int len = data.length;
		// 因为是两个元素交换位置，所以，外层只需要到len-2(即 < len-1)处。
		int outLen = len - 1;
		int tmp;
		// 是否有数据交换
		boolean exchanged = true;
		// 最后一次交换元素的位置，默认是倒数第二个位置（即倒数第二和最后一个元素交换）
		int lastExchangePosition = len - 2;
		// i也可以理解为已经排好序的元素个数
		for (int i = 0; exchanged && i <= lastExchangePosition;) {

			// 内循环的次数为：未排好序元素的个数
			exchanged = false;
			for (int j = 0; j < outLen - i; j++) {
				if (data[j] > data[j + 1]) {
					// 升序，数字大的冒泡到右端
					// 相邻元素不相等，则交换
					tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;

					// 说明发生了交换。
					exchanged = true;

					// 记录故事发生的坐标 :-)
					lastExchangePosition = j;
				}
			}
		}
		return data;
	}


	private static int[] insertionSort(int data[]) {
		int len = data.length;
		int tmp;
		// i也可以理解为已经排好序的元素个数
		// 初始时，第一个元素data[0]是已排序的列表中的元素
		for (int i = 1, j = 0; i < len; i++) {
			// 将下一个未排序的元素和已排序的列表中的元素逐一比较
			tmp = data[i];

			// 内循环的次数为：已排序的列表中的元素，从后向前扫描
			// 逐个比较，找到小于tmp元素的位置
			for (j = i - 1; j >= 0 && data[j] > tmp; j--) {
				// 将已排好序的序列右移
				data[j + 1] = data[j];
			}
			// 此时j即tmp的索引，而且其他有序元素已经给它挪位了。
			// tmp放置到正确的位置，之所以j+1是因为内循环j--了。。。
			data[j + 1] = tmp;
		}
		return data;
	}


	private static int[] insertionSortV2(int data[]) {
		// 二分法插入排序，也叫折半插入排序，是稳定的排序算法，仅减少了key比较的次数
		// 使用折半查找法在已有序的序列中查找待排序元素的位置
		// 元素比较log(n)，最优时间复杂度：O(nlog(n))，平均和最差时间复杂度为O(n^2)
		int len = data.length;
		int tmp;
		// i也可以理解为已经排好序的元素个数
		// 初始时，第一个元素data[0]是已排序的列表中的元素
		for (int i = 1, left, right, middle; i < len; i++) {
			// 将下一个未排序的元素和已排序的列表中的元素逐一比较
			tmp = data[i];
			// 左边元素肯定是第0个元素(已排序的列表中第一个元素)；
			left = 0;
			// 最右边的元素是已排序的列表中最后一个元素
			right = i - 1;

			// 折半查找待排序元素的位置
			while (left <= right) {
				middle = (right + left) / 2;
				// 如果中间的元素 大于 待排序的元素data[i]
				if (data[middle] > tmp) {
					// 说明 data[i]在前半部分
					right = middle - 1;
				} else {
					// 在后半部分
					left = middle + 1;
				}
			}

			// left= 现在找到了data[i]应该在已排序的列表中的位置了

			// 将left右边的元素向后挪窝啊
			for (int j = i - 1; j >= left; j--) {
				// 将已排好序的序列右移
				data[j + 1] = data[j];
			}
			// tmp插入到正确的位置
			data[left] = tmp;
		}
		return data;
	}


	private static int[] shellSort(int data[]) {
		int len = data.length;
		// 步长（子序列的长度）
		int step = len / 2;
		// 步长每次都会按一定比率减小,step=1时就可以全部排序了。
		int tmp;
		for (step = len / 2; step > 0; step /= 2) {
			// 现在将分组后的子序列分别插入排序
			// 比如序列 data={1,3,-1,5,4}，步长为2,划分为=>
			// {
			// 1,3,
			// -1,5
			// 4
			// ｝
			// 即data可以分成两列（组）子序列｛1，-1,4｝和｛3，5｝

			// 外循环i可以理解为有多少列（比如步长为2，意味着有2列，那么每列（组）分别进行插入排序）

			// 一循环将一个子序列全部排好序，
			// 循环第一趟，完成子序列｛1，-1,4｝的插入排序：｛-1，1，4｝
			// 循环第而趟，完成子序列｛3，5｝的插入排序：｛3，5｝
			for (int i = 0; i < step; i++) {

				// 内循环为每列数据： data[j,j+step,j+step+step,....]的插入排序
				for (int j = i + step; j < len; j += step) {
					// 每个元素与自己列的数据进行直接插入排序
					// data[j]为未排序的元素，data[j-step]为有序子序列的最后一个元素
					if (data[j] < data[j - step]) {
						tmp = data[j];
						int k = j - step;
						// 查找插入位置
						while (k >= 0 && data[k] > tmp) {
							data[k + step] = data[k];
							k -= step;
						}
						// 插入到正确位置
						data[k + step] = tmp;
					}
				}
			}
		}
		return data;
	}


	private static int[] shellSortV2(int data[]) {
		int len = data.length;
		// 步长（子序列的长度）
		int step = len / 2;
		// 步长每次都会按一定比率减小,step=1时就可以全部排序了。
		int tmp;
		for (step = len / 2; step > 0; step /= 2) {
			// 现在将分组后的子序列分别插入排序
			// 比如序列 data={1,3,-1,5,4}，步长为2,划分为=>
			// {1,3,
			// -1,5
			// 4
			// ｝
			// 即data可以分成两组子序列｛1，-1,4｝和｛3，5｝

			// 从step处开始处理，总计循环次数=data.length-step=5-2=3

			// 一轮循环插入一个子序列中未排序的元素，子序列交替插入排序

			// 循环第一轮，子序列｛1，-1,4｝有序序列｛1｝与 -1插入排序，完成后，子序列为｛-1，1, 4｝
			// 循环第二轮，子序列｛3，5｝有序序列｛3｝与5插入排序，完成后，子序列为｛3，5｝
			// 循环第三轮，完成子序列｛1，-1,4｝有序序列｛-1，1｝与元素4插入排序，完成后，子序列为｛-1，1, 4｝

			for (int i = step; i < len; i++) {

				// j为已排序的子序列的最后一位，j+step=i=待排序的元素的位置
				for (int j = i - step; j >= 0 && data[j] > data[j + step]; j -= step) {
					// 通过swap交换将待排序元素data[i]交换到正确的位置
					tmp = data[j];
					data[j] = data[j + step];
					data[j + step] = tmp;
				}

			}
		}
		return data;
	}
}
