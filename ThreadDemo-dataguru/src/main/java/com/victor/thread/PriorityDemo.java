package com.victor.thread;

public class PriorityDemo {
	public static class HighPriority extends Thread{
		int count =0;
		public void run(){
			while(true){
				synchronized (PriorityDemo.class) {
					count++;
					if(count>100000){
						System.out.println("HighPriority finish");
						break;
					}
				}
			
			}
		}
	}
	
	public static class LowPriority extends Thread{
		int count =0;
		public void run(){
			while(true){
				synchronized (PriorityDemo.class) {
					count++;
					if(count>100000){
						System.out.println("LowPriority finish");
						break;
					}
				}
			
			}
		}
	}
	public static class MiddlePriority extends Thread{
		int count =0;
		public void run(){
			while(true){
				synchronized (PriorityDemo.class) {
					count++;
					if(count>100000){
						System.out.println("MiddlePriority finish");
						break;
					}
				}
			
			}
		}
	}
	
	public static void main(String[] args) {
		LowPriority l = new LowPriority();
		HighPriority h = new HighPriority();
		MiddlePriority m = new MiddlePriority();
		m.setPriority(Thread.NORM_PRIORITY);
		l.setPriority(Thread.MIN_PRIORITY);
		h.setPriority(Thread.MAX_PRIORITY);
		l.start();
		h.start();
		m.start();
	}
}
