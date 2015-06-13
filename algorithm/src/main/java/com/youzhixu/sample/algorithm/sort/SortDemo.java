package com.youzhixu.sample.algorithm.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author huisman
 * @createAt 2015年6月9日 上午8:39:00
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class SortDemo {

	public static void main(String[] args) {
		// int[] data = new int[] {-1, 3, 5, 1, 0, -3};
		int[] data = new int[] {1, 5, 7, 1, 5,59};
		System.out.println("before sort:" + Arrays.toString(data));
		// simpleSelectionSort(data);
		// simpleSelectionSortV2(data);
		// bubleSort(data);
		// bubbleSortV2(data);
		// insertionSort(data);
		// insertionSortV2(data);
		// shellSort(data);
		// shellSortV2(data);
		// quickSort(data);
//		quickSortV2(data, 0, data.length - 1);
//		mergeSort(data);
		
//		bucketSort(data, 3);
		radixSort(data);
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
		int step;
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

			// 一趟循环将一个子序列全部排好序，
			// 循环第一趟，完成子序列｛1，-1,4｝的插入排序：｛-1，1，4｝
			// 循环第二趟，完成子序列｛3，5｝的插入排序：｛3，5｝
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
							// 有序子序列中比它大的往右移
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
		int step;
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

	private static int[] quickSort(int data[]) {
		int len = data.length;
		quickSort(data, 0, len - 1);
		return data;
	}

	private static void quickSort(int data[], int fromIndex, int endIndex) {
		if (fromIndex < endIndex) {
			// 找到基准元素在序列中的位置
			int pivot = partition(data, fromIndex, endIndex);
			System.out.println("fromIndex=" + fromIndex + ",endIndex=" + endIndex + ",pivotIndex="
					+ pivot + "," + Arrays.toString(data));

			// 依据基准元素将序列划分成两个子序列，如此递归调用，出口是fromIndex=endIndex，说明全排好序了
			quickSort(data, fromIndex, pivot - 1);
			quickSort(data, pivot + 1, endIndex);

		}
	}

	private static void quickSortV2(int data[], int fromIndex, int endIndex) {
		while (fromIndex < endIndex) {
			// 找到基准元素
			int pivot = partitionV2(data, fromIndex, endIndex);
			System.out.println("fromIndex=" + fromIndex + ",endIndex=" + endIndex + ",pivotIndex="
					+ pivot + "," + Arrays.toString(data));
			quickSort(data, fromIndex, pivot - 1);
			// 将第二部分的递归转换为循环
			fromIndex = pivot + 1;
			// quickSort(data, pivot + 1, endIndex);
		}
	}

	private static int partition(int[] data, int left, int right) {
		// 子序列为data[right,left]，参照pivot进行分区后的子序列【左 <- pivot - > 右】
		// 从右向左扫描data，将比pivot小的交换到左边。
		// 同时从左向右扫描data，将pivot大的交换到右边

		// 我们选择data[left]为基准元素,也可以选择最后一个元素data[right]，或者随机选择。

		int pivot = data[left];
		int high = right;
		int low = left;
		int tmp;
		// 最终low =high时，data[low]=data[high]=pivot
		while (low < high) {
			while (low < high && data[high] >= pivot) {
				// 从右向左扫描data，找到比基准小的元素，说明>=high位置的元素都比基准大了，不用换到左边去；
				// 如果小，则交换到左边去
				high--;
			}
			// 此时high已经是<=pivot的元素，交换data[low]和data[high]
			tmp = data[high];
			data[high] = data[low];
			data[low] = tmp;

			while (low < high && data[low] <= pivot) {
				// 从左向右扫描data，找到比基准大的元素，说明 <=low位置的元素都被基准小了，不用换到右边去；
				// 如果大，则交换到右边去
				low++;
			}

			// 此时low已经是 >=pivot的元素，交换data[low]和data[high]
			tmp = data[low];
			data[low] = data[high];
			data[high] = tmp;

			// 也意味着[left,low]是小于pivot的
			// [hight,right]这个子序列是大于pivot的。
		}
		System.out.println("low=" + low + ",high=" + high);
		return low;
	}


	private static int partitionV2(int[] data, int left, int right) {
		// 子序列为data[right,left]，参照pivot进行分区后的子序列【左 <- pivot - > 右】
		// 从右向左扫描data，将比pivot小的交换到左边。
		// 同时从左向右扫描data，将pivot大的交换到右边

		// 我们选择data[left]为基准元素,也可以选择最后一个元素data[right]，或者随机选择。

		// 采用替换，而不是交换，将元素分布到pivot两端
		int pivot = data[left];

		int high = right;
		int low = left;
		// 最终low =high时，data[low]=data[high]=pivot
		while (low < high) {
			while (low < high && data[high] >= pivot) {
				// 从右向左扫描data，找到比基准小的元素；
				high--;
			}
			// 找到元素比pivot小的元素了
			if (low < high) {
				// 把data[high]替换到左边来
				data[low] = data[high];
				// 说明data[low]的位置已经被占用，low向右移动
				// data[low]代表的是大于pivot的数，data[high]代表的小于pivot的数。
				// 替换之后此时low位置的数肯定小于pivot，所以low向右移动
				low++;
			}

			while (low < high && data[low] < pivot) {
				// 从左向右扫描data，找到比基准大的元素；
				low++;
			}
			// 找到元素比pivot大的元素了
			if (low < high) {
				// 把data[high]换到右边来
				data[high] = data[low];
				// 说明data[high]的位置已经被占用，high向左移动
				// data[high]代表的小于pivot的数，data[low]代表的是大于pivot的数。
				// 替换之后此时high位置的数肯定大于pivot，所以high向左移动
				high--;
			}
		}
		// 此时low=high,即pivot的位置
		data[low] = pivot;
		return low;
	}

	private static int[] mergeSort(int[] data) {
		int[] tmpArray=new int[data.length];
		mergeSort(data, 0, data.length-1, tmpArray);
		return data;
	}

	private static void mergeSort(int[] data, int fromIndex, int endIndex, int tmpArray[]) {
		if (fromIndex < endIndex) {
			//递归划分成两个数组，并合并（两路归并递归算法）
			int middle = (fromIndex + endIndex) / 2;
			 // 左边有序
			mergeSort(data, fromIndex, middle, tmpArray);
			 // 右边有序
			mergeSort(data, middle + 1, endIndex, tmpArray);
			 // 再将二个有序数列合并
			mergeArray(data, fromIndex, middle, endIndex, tmpArray);
		}
	}

	private static void mergeArray(int[] data, int fromIndex, int middleIndex, int endIndex,
			int[] tmpArr) {
		// 将data[fromIndex,middleIndex]
		// 和data[middleIndex+1,endIndex]两个数组
		// 合并到临时数组中tmpArr
		int i = fromIndex, j = middleIndex + 1;
		// k为临时数组tmpArr的下标
		int k = 0;
		// 先后遍历两个数组，因为递归之后每个数组都是有序的，
		// 例如：前半部分数组data[fromIndex,middleIndex]={-3,4,8}
		// 而后半部分数组data[middleIndex+1,endIndex]={-1,8}
		// 按从小到大的顺序合并到临时数组tmpArr中｛-3，-1，4，8，8｝
		while (i <= middleIndex && j <= endIndex) {
			// 比较两个数组中的元素，升序
			if (data[i] <= data[j]) {
				tmpArr[k++] = data[i++];
			} else {
				tmpArr[k++] = data[j++];
			}
		}
		// 如果上面前半部分没合并完
		// i表示data[fromIndex,middleIndex]已有多少个元素添加到tmpArr了
		while (i <= middleIndex) {
			tmpArr[k++] = data[i++];
		}
		// 如果上面后半部分没合并玩
		// j表示data[middleIndex+1,endIndex]已有多少个元素添加到tmpArr了
		while (j <= endIndex) {
			tmpArr[k++] = data[j++];
		}

		// 现在将临时数组中的有序数组，覆盖原来的[fromIndex,endIndex]之间的元素
		for (i = 0; i < k; i++) {
			data[fromIndex + i] = tmpArr[i];
		}
	}

	private static int[] bucketSort(int[] data, int bucketCount) {
		    //bucketCount 桶的个数，负数需要特别处理，比如专门申请一些桶，取绝对值放置
			int len=data.length;
	        int high =data[0];
	        int low = data[0];
	        //查找数组元素中最大值和最小值,进而确定数据的分布范围 O(n);
	        // 不一定需要准确的区间，你也可以指定区间，不过这个区间要包含所有待排序的数据
	        for (int i = 1; i < len; i++) {
	            if (data[i] > high){
	            	high = data[i];
	            }
	            if (data[i] < low){
	            	low = data[i];
	            }
	        }
	        
	        //每个桶有多少个数据
	        int bucketSize = (high - low + 1)/bucketCount;
	        //桶
	        List<Integer>[] buckets = new ArrayList[bucketSize];
	        
	        System.out.println("bucketSize="+bucketSize);
	        //现在开始遍历元素，将每个元素分到该去的桶里
	        for (int i = 0; i < len; i++) { 
	        	//要放到那个位置的桶里
	        	int bucketIndex=(data[i] - low) /bucketSize;
	        	System.out.println(data[i]+"要放到： bucketIndex="+bucketIndex);
	        	if (buckets[bucketIndex] == null) {
	        		//初始化
					buckets[bucketIndex]= new ArrayList<Integer>(bucketSize);
				}
	        	//放到这个桶里
	        	buckets[bucketIndex].add(data[i]);
	        }
	        //已有序的元素
	        int current=0;
	        //现在我们开始按顺序遍历整个桶，并排序，
	        for (int i = 0; i < buckets.length; i++) {
				if (buckets[i] !=null) {
					//说明桶里有元素，那我们开始排序,可以替换为快速排序等
					Collections.sort(buckets[i]);
					//排好序了。。把数据放回到原来的数组里（覆盖）
					int bucketLen=buckets[i].size();
					for (int j = 0; j < bucketLen; j++) {
						data[current++]=buckets[i].get(j);
					}
				}
			}
	        
	        return data;
	    }

	  private static int[] radixSort(int[] data){
		  	 int len=data.length;
			 //数组中最大的元素
			 int maxNum=data[0];
			
			 //先找到最大元素
			  for (int i = 1; i < len; i++) {
			     if (data[i] > maxNum) {
				 maxNum=data[i];
			     }
		 	   }
				
			   //计算最大元素的位数，位数决定了比较的次数
			   int numLen=1;
			   while((maxNum=maxNum /10) !=0){
				   numLen++;
			   }
			   
			   //从低位到高位，根据每位数【个，十，百，千,....】的大小排序	   
			   //数字范围为：【0，1，2，3，4，5，6，7，8，9】，使用10个桶，统计指定位digit出现的次数， 
			   int[] buckets=new int[10];

		           //临时数组，存放每趟排序后的数据
			   int[] tmpArr=new int[len];

			   //外层循环取决于最大数字的位数
			   for (int i = 0; i < numLen; i++) {

				   //0表示没数据落进来
				   //每轮循环时清空buckets的计数
				   Arrays.fill(buckets, 0);
				   
				   //遍历每个数据，放到可以计数的桶里
				   for (int j = 0; j < len; j++) {
					   //算出指定数位的值digit
					   int digit=(data[j]/ (int)Math.pow(10,i))%10;

					   //累加digit所代表的数字的出现次数
					   buckets[digit]++;
				   }
				   
				   //将tmp中的位置依次分配给每个桶  

				   // buckets[]数组中的digit出现的次数累加之和就是待排序数组元素的数量len
				   // 即将data[0]+data[1]+...+data[9]= len
				  for (int j = 1; j < buckets.length; j++) {
					  //累计每个bucket[j]，统计bucket[j]代表的数组元素的个数
					  //假如 digit=3,buckets[digit]=buckets[3]=7，那么说明待排序数组有7个元素，落在bucket[0,3]
					  buckets[j] =buckets[j]+buckets[j-1];
				  }
				  
				   //将所有桶中记录依次收集到tmp中
				  for (int j =len-1 ; j >=0; j--) {
					  //算出指定数位的值digit
					   int digit=(data[j]/ (int)Math.pow(10,i))%10;

					  //比如digit=3,而上一步我们计算得出bucket[digit]=bucket[3]=7,7个元素落在bucket[0,3]
					  //那我们只需要将元素按顺序放置在临时数组的[0,7)之间，它们自然就是有序的
					  tmpArr[buckets[digit] -1] = data[j];

					  //将此digit位置的计数器减一，下一个相同的digit则放置在相邻的位置
					  buckets[digit] --;
				  }
				  
				 //此时元素已经按照digit排好序了，将临时数组复制到data中，
				 System.arraycopy(tmpArr,0,data,0,len);;
			   }

			   return data;
			}

}
