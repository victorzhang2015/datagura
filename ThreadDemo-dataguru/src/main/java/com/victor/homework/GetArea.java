package com.victor.homework;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GetArea {
	private static BlockingQueue<Double> queue = new ArrayBlockingQueue<Double>(100000);
	static double i=1; 
	static double area=0.0;
	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(5);
		es.submit(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean isRunning =true;
				while(isRunning){
					if(i>100){
						isRunning =false;
						break;
					}
					i+=0.01;
					System.out.println("插入的i为"+i);
					try {
						if(!queue.offer(i,2,TimeUnit.SECONDS)){
							System.out.println("failed to put data"+i);
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		ExecutorService es1 = Executors.newFixedThreadPool(5);
		es1.submit(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				double x = 0;
				boolean flag =true;
				while(flag){
					if(x>100){
						flag =false;
						System.out.println("面积为"+area);
						break;
					}
					try {
						x = queue.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					double y = 1/(x+1);
					System.out.println("y的值为"+y);
					area = 0.01*y+area;
				}
			}
		});
	}
}
