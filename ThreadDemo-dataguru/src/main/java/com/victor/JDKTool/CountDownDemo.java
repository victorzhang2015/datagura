package com.victor.JDKTool;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownDemo implements Runnable {
	static final CountDownLatch end = new CountDownLatch(10);
	static final CountDownDemo demo = new CountDownDemo();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			Thread.sleep(new Random().nextInt(10)*1000);
			System.out.println("check complete");
			end.countDown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

		public static void main(String[] args) throws InterruptedException {
			ExecutorService exec = Executors.newFixedThreadPool(10);
			for(int i=0;i<10;i++){
				exec.submit(demo);
			}
			end.await();
			System.out.println("Fire");
			exec.shutdown();
		}
}
