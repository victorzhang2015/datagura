package com.victor.JDKTool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingQueneDemo implements Runnable{
	static 	BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100000);
	static final BlockingQueneDemo demo = new BlockingQueneDemo();
	static int i =0;
	@Override
	public void run() {
		//queue.put(i);
		queue.offer(i);
		i++;
	}
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(10);
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
