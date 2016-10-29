package com.victor.JDKTool;

import java.util.concurrent.locks.ReentrantLock;

public class Reenterlock implements Runnable{
	public static ReentrantLock lock = new ReentrantLock();
	public static int i=0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int j=0;j<100000;j++){
			lock.lock();
			try{
				i++;
			}finally{
				lock.unlock();
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread( new Reenterlock());
		t1.start();t1.join();
		System.out.println(i);
	}
}
