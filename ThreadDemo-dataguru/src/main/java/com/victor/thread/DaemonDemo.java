package com.victor.thread;

public class DaemonDemo {
	public static class DaemonT extends Thread{
		public void run(){
			while(true){
				System.out.println("I'm active £¡");
				try {
					Thread.currentThread().sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	public static void main(String[] args) {
		Thread t = new DaemonT();
		t.setDaemon(true);
		t.start();
	}
}
