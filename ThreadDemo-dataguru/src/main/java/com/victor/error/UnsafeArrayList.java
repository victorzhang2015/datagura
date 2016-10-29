package com.victor.error;

import java.util.ArrayList;

public class UnsafeArrayList {
	static ArrayList<Object> al = new ArrayList<Object>();
	static class AssTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<1000000000;i++){
				al.add(new Object());
			}
		}
		
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new AssTask(),"t1");
		Thread t2 = new Thread(new AssTask(),"t2");
		t1.start();
		t2.start();
		Thread t3 = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
						// TODO Auto-generated method stub
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for(int i=0;i<100000;i++){
							al.add(new Object());
						}
				}
			}
			
		});
	}
}
