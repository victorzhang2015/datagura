package com.victor.JDKTool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentLinkedQueueDemo implements Runnable{
	static 	ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
	static final ConcurrentLinkedQueueDemo demo = new ConcurrentLinkedQueueDemo();
	static int i =0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		queue.add(i);
		i++;
	}
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(1);
		for(int j=0;j<1000000;j++){
			exec.submit(demo);
			if(i>=100000){
				System.out.println("i1"+i);
				long time2 = System.currentTimeMillis();
				System.out.println(" ±º‰≤Ó÷µ"+(time2-time));
				exec.shutdown();
				break;
			}
		}
		System.out.println("i2"+i);
		exec.shutdown();
		
	
	}
	
}
