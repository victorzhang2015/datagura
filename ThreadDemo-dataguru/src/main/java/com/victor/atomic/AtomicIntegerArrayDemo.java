package com.victor.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
	//static AtomicIntegerArray arr = new AtomicIntegerArray(10);
	static int[] arr = new int[10];
	public static class addThread implements Runnable{
		public void run(){
			for(int k=0;k<10000;k++){
				arr[k%arr.length]=k++;
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] ts = new Thread[10];
		for(int k =0;k<10;k++){
			ts[k]=new Thread( new addThread() );
		}
		for(int k =0;k<10;k++){
			ts[k].start();
		}
		for(int k =0;k<10;k++){
		ts[k].join();
		}
		for(int i=0;i<arr.length;i++){
			System.out.println(arr[i]);
		}
		
	}
}
