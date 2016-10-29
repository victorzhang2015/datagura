package com.victor.thread;


public class QueueDemo {
	static int[] array = new int[5];
	static boolean flags = true;
	
	public static void init(){
		for(int i=0;i<5;i++){
				array[i]=1;
		}
	}
	
	public static void add(int num){
		for(int i=0;i<5;i++){
			if(array[i]==1){
				array[i]=num;
				System.out.println("ͨ��add��������һ����Ԫ��");
				break;
			}
		}
	}
	public static int get(){
		int num=0;
		for(int i=0;i<5;i++){
			if(array[i]!=1){
				num = array[i];
				array[i]=1;
				System.out.println("ͨ��get�������һ��Ԫ��"+num);
				return num;
			}
		}
		return num;
	}
	public static class T1 extends Thread{
		public void run(){
			System.out.println("thread1 start");
			synchronized (array) {
				System.out.println("thread1 start");
				while(true){
					add(2);
					boolean flag =true;
					for(int i=0;i<5;i++){
						if(array[i]!=2){
							flag=false;
						}
					}
					if(flag){
							System.out.println("add�����������������Ѿ���");
							System.out.println("get����������");
							try {
								array.wait(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				}
				}
			}
		}
	}
	
	public static class T2 extends Thread{
		public void run(){
			synchronized (array) {
				System.out.println("thread2 start");
				while(true){
					get();
					boolean flag =true;
					for(int i=0;i<5;i++){
						if(array[i]!=1){
							flag=false;
						}
					}
					if(flag){
							System.out.println("get����������������Ϊ��");
							System.out.println("add����������");
							try {
								array.wait(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}
		}
	}
	public static void main(String[] args)  {
		QueueDemo queueDemo = new QueueDemo();
		queueDemo.init();
		Thread t1 = new T1();
		Thread t2 = new T2();
		t1.start();
		t2.start();
	}
	
}
