package com.victor.thread;


public class QueueDemo1 implements Runnable{
	static int[] array = new int[10];
	public void init(){
		for(int i=0;i<10;i++){
				array[i]=1;
		}
	}
	
	public static void add(int num){
		for(int i=0;i<10;i++){
			if(array[i]==0){
				array[i]=num;
				System.out.println("通过add方法增加一个新元素");
				break;
			}
		}
	}
	public static int get(){
		int num=0;
		for(int i=0;i<10;i++){
			if(array[i]!=1){
				num = array[i];
				array[i]=1;
				System.out.println("通过get方法获得一个元素");
				break;
			}
		}
		return num;
	}
	public static class T1 extends Thread{
		public void run(){
			synchronized (array) {
				add(2);
				boolean flag =true;
				for(int i=0;i<10;i++){
					if(array[i]!=2){
						flag=false;
					}
				}
				if(flag){
					try {
						System.out.println("add方法被阻塞，队列已经满");
						array.wait();
						Thread.currentThread().sleep(2000);
						array.notify();
						System.out.println("get方法被开启");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static class T2 extends Thread{
		public void run(){
			synchronized (array) {
				get();
				boolean flag =true;
				for(int i=0;i<10;i++){
					if(array[i]!=1){
						flag=false;
					}
				}
				if(flag){
					try {
						array.wait();
						System.out.println("get方法被阻塞，队列为空");
						Thread.currentThread().sleep(2000);
						array.notify();
						System.out.println("add方法被开启");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		QueueDemo1 queueDemo = new QueueDemo1();
		queueDemo.init();
		System.out.println(queueDemo.array);
		System.out.println("初始化完毕");
		Thread t1 = new T1();
		//Thread t2 = new T2();
		t1.start();
		//t2.start();
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
