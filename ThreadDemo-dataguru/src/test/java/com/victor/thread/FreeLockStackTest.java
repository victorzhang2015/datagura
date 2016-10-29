package com.victor.thread;

import org.junit.Test;

import com.victor.atomic.FreeLockStack;
import com.victor.atomic.Stack;

public class FreeLockStackTest{
	static  FreeLockStack<Integer> stack=new FreeLockStack<Integer>();
	//static  Stack stack=new Stack();
	 volatile static int i =1;
	 class pushThread implements Runnable{
		public void run(){
			push();
		}
		
		public void push(){
		//	synchronized (pushThread.class){
				int k =i++;
				String name =Thread.currentThread().getName();
				System.out.println(name+"======push==="+k);
				stack.push(k);
		//	}
		}
	}
	 class popThread implements Runnable{
			public void run(){
				pop();
			}
			
			public void pop(){
			//	synchronized (popThread.class){
					String name =Thread.currentThread().getName();
					System.out.println(name+"======pop==="+stack.pop());
			//	}
			}
		}
   @Test
	public void TestFreeLockStack() throws InterruptedException{
	   Thread[] t = new Thread[10];
	   Thread[] t1 = new Thread[10];
	   for(int i=0;i<10;i++){
		   t[i]=new Thread(new pushThread());
		   t1[i]=new Thread( new popThread());
	   }
	   for(int i=0;i<10;i++){
		//  t1[i]=new Thread( new popThread());
	   }
	   for(int i=0;i<10;i++){
		   t[i].start();
		   t[i].join();
		   t1[i].start();
		   t1[i].join();
	   }
	   System.out.println("stack"+stack.toString());
	}
}