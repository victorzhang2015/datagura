package com.victor.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	public static class Candidate{
		int id;
		volatile int score;
	}
	public final static AtomicIntegerFieldUpdater<Candidate> socreUpdater =AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");
	static AtomicInteger allScore = new AtomicInteger();
	public static void main(String[] args) throws InterruptedException {
		final Candidate stu = new Candidate();
		Thread[] t = new Thread[10000];
		for(int i=0;i<10000;i++){
			t[i]=new Thread(){
				public void run(){
					if(Math.random()>0.4){
						socreUpdater.incrementAndGet(stu);
						allScore.incrementAndGet();
					}
				}
			};
			t[i].start();
		}
		
		for(int i=0;i<10000;i++){
			t[i].join();
		}
		System.out.println("socre="+stu.score);
		System.out.println("allScore="+allScore);
	}
	
}
