package com.youzhixu.sample.algorithm.bit;

/**
 * <p>
 * bit的一些常用技巧
 * </p>
 * 
 * @author huisman
 * @since 1.0.0
 * @createAt 2015年6月5日 下午10:43:09
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */
public class BitDemo {


	public static void main(String[] args) {
		// System.out.println(abs(Integer.MIN_VALUE));
		// System.out.println(min(-5,-3));
		// System.out.println(minv(-4,-1));
		// System.out.println(max(2,-3));
		// System.out.println(maxv(4,-1));
//		System.out.println(isPowOf2(64));
		int[]	numbers=new int[]{-1,2};
		for (int num : numbers) {
			System.out.println("num="+num+",method=v1，result="+countingBitsV1(num));
//			System.out.println("num="+num+",method=v2，result="+countingBitsV2(num));
//			System.out.println("num="+num+",method=v3，result="+countingBitsV3(num));
		}
		swapV1(Integer.MIN_VALUE, 15);
		swapV2(Integer.MIN_VALUE, 15);
	}

	/**
	 * 使用位移计算整数的绝对值 为了防止溢出，返回long（Java 没有无符号） 比如Integer.MIN_VALUE = 0x80000000=-2147483648，
	 * MAX_VALUE = 0x7fffffff=2147483647，会出现溢出。
	 * 
	 * @param num
	 * @return
	 * @since: 1.0.0
	 */
	private static long abs(int num) {
		// 计算出最高位的符号（0，或者-1，算术右移，左边补符号位）
		int signal = num >> (Integer.SIZE - 1);
		// 对于任何数，与0异或（相同为0，相异为1，异或常用来反转位，即取反）都会保持不变，
		// 对于任何数与-1即0xFFFFFFFF异或，就相当于取反

		// 对于一个负数a来说，~a+1则为其绝对值，需要防止类型提升之前的溢出
		return (num ^ signal) - (long) signal;
	}

	private static int min(int a, int b) {
		// 判断两个数的符号位是否相同，符号不同时减法才可能溢出
		// 根据异或特性，符号位相同为0，符号位不同时为1
		int signalSame = ((a ^ b) >> (Integer.SIZE - 1)) & 1;
		// a的最高位，如果a<0，则asignal=1，否则asignal=0
		int asignal = (a >> (Integer.SIZE - 1)) & 1;
		// a-b后的符号位,如果a-b <0,abSignal=1，否则abSignal=0
		int abSignal = ((a - b) >> (Integer.SIZE - 1)) & 1;
		// 当a,b符号位相同时,signalSame=0，结果返回的是 (1-signal)*(abSignal*b +
		// (1-abSignal)*a),此时(a-b)不溢出，可以使用abSignal的结果了。
		// 当a,b符号位不同时,signalSame=1，结果返回的是signalSame*(asignal*b +
		// (1-asignal)*a)，此时(a-b)可能溢出，可以使用asignal；
		return signalSame * (asignal * a + (1 - asignal) * b) + (1 - signalSame)
				* (abSignal * a + (1 - abSignal) * b);
	}

	private static int max(int a, int b) {
		// 判断两个数的符号位是否相同，符号不同时减法才可能溢出
		// 根据异或特性，符号位相同为0，符号位不同时为1
		int signalSame = ((a ^ b) >> (Integer.SIZE - 1)) & 1;

		// a的最高位，如果a<0，则asignal=1，否则asignal=0
		int asignal = (a >> (Integer.SIZE - 1)) & 1;

		// a-b后的符号位,如果a-b <0,abSignal=1，否则abSignal=0
		int abSignal = ((a - b) >> (Integer.SIZE - 1)) & 1;

		// 当a,b符号位相同时,signalSame=0，
		// 结果返回的是 (1-signal)*(abSignal*b + (1-abSignal)*a),此时(a-b)不溢出，可以使用abSignal的结果了。

		// 当a,b符号位不同时,signalSame=1，
		// 结果返回的是signalSame*(asignal*b + (1-asignal)*a)，此时(a-b)可能溢出，可以使用asignal；
		// 和min(a,b)的思路一样，只不过交换了返回的a、b。
		return signalSame * (asignal * b + (1 - asignal) * a) + (1 - signalSame)
				* (abSignal * b + (1 - abSignal) * a);
	}


	private static int minv(int a, int b) {
		// 注意以下逻辑是C语言中的，C语言没有boolean类型，逻辑值转化为0或者1
		// min(x, y)=y ^ ((x ^ y) & -(x < y))，
		// 推导如下：
		// 如果x < y，那么 -（x<y)=-1=~0，那么r=y ^ ((x ^ y) & -(x < y))=y ^ (x ^ y) & ~0= y ^ x ^ y = x
		// (一个数自己异或自己结果为0，对于任何数，与0异或都不变)；
		// 如果 x >=y，那么 -(x<y)=-0=0，r = y ^ ((x ^ y) & 0) = y (任何数与0 & 都为0);

		// 应用到Java中，则需要判断两个数相减后的符号位来判断
		// 比如： 如果x < y，那么 -（x<y)其实等于 (x-y)的符号位-1; 如果x >= y,那么 -（x<y)也是 (x-y)的符号位=0；
		// 也就是说我们其实只需要判断（x-y）的符号位，再结合异或的性质就可以判断两个数字的大小了
		// 但是 x-y会溢出比如Integer.MIN_VALUE = 0x80000000=-2147483648 -5 会溢出成 2147483643，就不能根据符号位判断大小了
		// 当a,b符号相同时不会溢出，a，b符号不同时可能溢出，为了防止溢出，我们可以将a或者b类型提升为long...
		long signal = (a - (long) b) >> (Long.SIZE - 1);// (a-b)>>63
		return b ^ ((a ^ b) & (int) signal);
	}

	private static int maxv(int a, int b) {
		// 注意以下逻辑是C语言中的，C语言没有boolean类型，逻辑值转化为0或者1
		// max(x, y)=x ^ ((x ^ y) & -(x < y))，
		// 推导如下：
		// 如果x < y，那么 -（x < y)=-1=~0，
		// 那么r=x ^ ((x ^ y) & -(x < y))=x ^ (x ^ y) & ~0= x ^ x ^ y = y
		// (一个数自己异或自己结果为0，对于任何数，与0异或都不变,-1所有位都是1)；

		// 如果 x > = y，那么 -(x < y)=-0=0，r = x ^ ((x ^ y) & 0) = x (任何数与0 & 都为0);

		// 应用到Java中，则需要判断两个数相减后的符号位来判断
		// 比如： 如果x < y，那么 -（x < y)其实等于 (x-y)的符号位-1; 如果x >= y,那么 -（x < y)也是 (x-y)的符号位=0；
		// 也就是说我们其实只需要判断（x-y）的符号位，再结合异或的性质就可以判断两个数字的大小了
		// 但是 x-y会溢出比如Integer.MIN_VALUE = 0x80000000=-2147483648 -5 会溢出成 2147483643，
		// 就不能根据符号位判断大小了

		// 确切的说：当a,b符号相同时不会溢出，a与b符号不同时可能溢出，为了防止溢出，我们可以将a或者b类型提升为long...
		long signal = (a - (long) b) >> (Long.SIZE - 1);// (a-b) >> 63
		return a ^ ((a ^ b) & (int) signal);
	}


	private static boolean isPowOf2(int num) {
		// C语言非0代表true,配合&&短路，可以直接返回 (v && !(v & (v - 1)));
		if (num <= 0) {
			return false;
		}
		// 我们归纳一下，满足2^n次方的数据：
		// 2^0=1 ， 二进制为 0000 0001，2^0 -1 =0， 二进制为 0000 0000;
		// 2^1=2 ， 二进制为 0000 0010，2^1 -1 =1， 二进制为 0000 0001
		// 2^2=4 ， 二进制为 0000 0100，2^2 -1 =3， 二进制为 0000 0011
		// 2^3=8 ， 二进制为 0000 1000，2^3 -1 =7， 二进制为 0000 0111
		// 2^4=16 ，二进制为 0001 0000，2^4 -1 =15，二进制为 0000 1111

		// 也就是说 2^n次方的二进制结果为：100..00(1后有n个0)；
		// 2^n-1的二进制结果为：011...11（n-1个1）；

		// 则 2^n & (2^n -1) =0；0和负数例外。
		return (num & (num - 1)) == 0;
	}

	private static int countingBitsV1(int num) {
		int count = 0;
		// 对于整数，逐位右移,右移一位，会丢掉最右边的bit，一定要无符号右移，否则负数的时候，高位补1，陷入死循环
		for (count = 0; num != 0; num >>>= 1) {
			count += (num & 1);
		}
		return count;
	}
	
	private static int countingBitsV2(int num) {
		int count = 0;
		// x=x&(x-1)会将x用二进制表示时最右边的一个1变为0，因为x-1会将该位变为0，类似右移一位
		while(num !=0){
			num = num&(num-1);
			count++;
		}
		return count;
	}
	

	private static int countingBitsV3(int num) {
//		0x55555555 = 01010101 01010101 01010101 01010101
//		0x33333333 = 00110011 00110011 00110011 00110011
//		0x0F0F0F0F = 00001111 00001111 00001111 00001111
//		0x00FF00FF = 00000000 11111111 00000000 11111111
//		0x0000FFFF = 00000000 00000000 11111111 11111111
		//用了分治的思想，先计算每对相邻的2位中有几个1，
		//再计算每相邻的4位中有几个1，
		//再计算8位、16位、32位
		//因为2^5＝32，所以对于32位的整数来说，5条位运算语句就够了
		
		num = (num&0x55555555) + ((num>>1)&0x55555555); 
		num = (num&0x33333333) + ((num>>2)&0x33333333); 
		num = (num&0x0f0f0f0f) + ((num>>4)&0x0f0f0f0f); 
		num = (num&0x00ff00ff) + ((num>>8)&0x00ff00ff); 
		num = (num&0x0000ffff) + ((num>>16)&0x0000ffff);
		
	    return num;
	}

	private static void swapV1(int a, int b){
		//(&(a) == &(b)) || (((a) -= (b)), ((b) += (a)), ((a) = (b) - (a))))
		System.out.println("before swap:a="+a+",b="+b);
		if (a != b) {
			a-=b;//a = a -b;
			b+=a;// b = b+a= b+a-b=a;
			a=b-a;// a = b-a =a -(a-b) =b;
		}
		System.out.println("after swap:a="+a+",b="+b);
	}
	
	private static void swapV2(int a, int b){
		//((a) ^= (b)), ((b) ^= (a)), ((a) ^= (b)))
		System.out.println("before swap:a="+a+",b="+b);
		if (a != b) {
			a ^=b;// a = a^b;
			b ^=a;// b =b^a=b^a^b=a;
			a ^=b;// a =a^b=a^b^a=b;
		}
		System.out.println("after swap:a="+a+",b="+b);
	}
	
	private static int nextPowerOf2(int n) {
		//每次迭代都会使1的位数翻倍，2的5次方为32，所以对于32位系统需要循环右移5次。同理对于64位系统需要循环右移6次
		//算法不用考虑溢出
	    n -= 1;
	    n |= n >>> 16;
	    n |= n >>> 8;
	    n |= n >>> 4;
	    n |= n >>> 2;
	    n |= n >>> 1;
	    return n + 1;
	}
	
	private static boolean isEven(int num){
		//校验最低位是否是0
		return (num & 1) == 0;
	}
	
}
