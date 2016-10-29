package com.victor.error;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
	 public static void main(String[] args) {
	  final Queue3 q3 = new Queue3();
	  long start =System.currentTimeMillis();
	 
	  for (int i=0; i <20; i++) {
	   new Thread() {
	    public void run() {
	      q3.get();
	    }
	   }.start();
	  }
	  new Thread() {
		    public void run() {
		      //传入一个data值
		      q3.put(new Random().nextInt(10000));
		    }
		   }.start();
		   int i =q3.getData();
		   while(i==-1){
			   i=q3.getData();
			   System.out.println("data"+q3.getData());
			}
			 long end = System.currentTimeMillis();
			  System.out.println("耗时"+(end-start));
	 }
}


class Queue3{
 
 private Integer data = -1;//共享數據，只能有一个线程能写该数据，但有多个线程能读该数据
 private StampedLock rw1 = new StampedLock();
 
 /**
 * @return the data
 */
public Integer getData() {
	return data;
}

/**
 * @param data the data to set
 */
public void setData(Integer data) {
	this.data = data;
}

/**
 * @return the rw1
 */
public StampedLock getRw1() {
	return rw1;
}

/**
 * @param rw1 the rw1 to set
 */
public void setRw1(StampedLock rw1) {
	this.rw1 = rw1;
}

public void get(){ 
	long stamp =rw1.tryOptimisticRead();
	if(!rw1.validate(stamp)){
		stamp=rw1.readLock();
		 try{
			   Thread.sleep((long) (Math.random()*1000));
		}catch (Exception e) {
			    e.printStackTrace();
		}finally{
			rw1.unlockRead(stamp);
		}
		System.out.println(Thread.currentThread().getName()+"have read data: " + data);
	}
	System.out.println(Thread.currentThread().getName()+" be ready to read data!");
	System.out.println(Thread.currentThread().getName()+"have read data: " + data);
   
 }
 
 public void put(Integer data){ 
   long stamp=rw1.writeLock();
   System.out.println(Thread.currentThread().getName()+" be ready to write data!");
   try{
    Thread.sleep((long) (Math.random()*1000));
   }catch (Exception e) {
    e.printStackTrace();
   }
   this.data = data;
   System.out.println(Thread.currentThread().getName()+"have write data: "+ data);
   rw1.unlockWrite(stamp);
 }
}