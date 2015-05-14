package com.youzhixu.sample.concurrent;

/**
 * <p>
 *
 * </p>
 * 
 * @author huisman
 * @createAt 2015年5月14日 上午10:10:01
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class UnknowFeatureDemo {
	// Private member variable
	private String privateMemberVariable = null;

	// Private member method
	private String privateMethod() {
		return privateMemberVariable;
	}

	public UnknowFeatureDemo(String str) {
		privateMemberVariable = str;
	}

	public void demoAccessOtherClass(UnknowFeatureDemo otherInstance) {
		// Access private members of second instance
		System.out.println("Private member variable :" + otherInstance.privateMemberVariable);
		System.out.println("Private member method :" + otherInstance.privateMethod());
	}

	public static void main(String[] args) {
		UnknowFeatureDemo firstInstance = new UnknowFeatureDemo("first instance");
		UnknowFeatureDemo secondInstance = new UnknowFeatureDemo("second instance");
		firstInstance.demoAccessOtherClass(secondInstance);
	}

}
