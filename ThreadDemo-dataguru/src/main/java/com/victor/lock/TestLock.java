package com.victor.lock;

import java.util.List;
import java.util.Vector;


public class TestLock {
	//public static List<Integer> numberList = new Vector<Integer>();
	public static StringBuffer buff = new StringBuffer();
	public static void main(String[] args) {
		long begin =System.currentTimeMillis();
		int count =0;
		int startnum =0;
		while(count <4000000){
			buff.append(startnum+"");
			//numberList.add(startnum);
			startnum+=2;
			count++;
		}
		long end = System.currentTimeMillis();
		//System.out.println("开启偏向锁持续时间"+(end-begin));
		System.out.println("禁用偏向锁持续时间"+(end-begin));
		
	}
	
}
