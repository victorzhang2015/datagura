package com.victor.thread;

public class AccountingSync3 implements Runnable{
	static int i =0;
	//synchronized 加在静态方法上可以保证一致性
	public static synchronized void increase(){
		i++;
	}
	public void run() {
		// TODO Auto-generated method stub
		for(int j=0;j<10000;j++){
			increase();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new AccountingSync3());
		Thread t2 = new Thread(new AccountingSync3());
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i);
	}
}
