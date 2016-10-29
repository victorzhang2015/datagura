package com.victor.thread;

public class AccountingSync2 implements Runnable{
	
	static int i =0;
	//synchronized 加在实例方法上,调用该方法的实例必须是一个
	public synchronized void increase(){
		i++;
	}
	public void run() {
		// TODO Auto-generated method stub
		for(int j=0;j<10000;j++){
			increase();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		AccountingSync2 instance = new AccountingSync2();
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
