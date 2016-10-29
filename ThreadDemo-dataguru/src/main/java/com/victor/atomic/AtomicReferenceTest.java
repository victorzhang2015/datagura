package com.victor.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {
	static AtomicReference<String> atomicStr = new AtomicReference<String>("abc");
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			new Thread(){
				public void run(){
					try {
						Thread.sleep(Math.abs((int)(Math.random()*100)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(atomicStr.compareAndSet("abc", "def")){
						System.out.println("Thread"+Thread.currentThread().getId()+"change successful");
					}else{
						System.out.println("Thread"+Thread.currentThread().getId()+"change fail");
					}
				}
			}.start();
			
		}
	}
}
