package com.victor.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
	//static AtomicInteger i = new AtomicInteger();
	static int i=0;
	public static class AddThread implements Runnable{
		public void run(){
			for(int k=0;k<10000;k++){
			//	i.incrementAndGet();
				i++;
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread[] t = new Thread[10];
		for(int k=0;k<10;k++){
			t[k]=new Thread(new AddThread());
		}
		for(int k=0;k<10;k++){
			t[k].start();
			t[k].join();
		}
		System.out.println(i);
	}
}
