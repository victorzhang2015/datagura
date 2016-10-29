package com.victor.error;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLockCar extends Thread {
		protected Object myDirect;
		static ReentrantLock south = new ReentrantLock();
		static ReentrantLock north = new ReentrantLock();
		static ReentrantLock west = new ReentrantLock();
		static ReentrantLock east = new ReentrantLock();
		private DeadLockCar(Object myDirect) {
			this.myDirect = myDirect;
			if(myDirect==south){
				this.setName("south");
			}
			if(myDirect==north){
				this.setName("north");
			}
			if(myDirect==west){
				this.setName("west");
			}
			if(myDirect==east){
				this.setName("east");
			}
			
		}
		
		public void run(){
			if(myDirect==north){
				try {
					east.lockInterruptibly();
					Thread.sleep(500);
					north.lockInterruptibly();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(north.isHeldByCurrentThread()){
						north.unlock();
					}
					if(east.isHeldByCurrentThread()){
						east.unlock();
					}
				}
			}
			if(myDirect==east){
				try {
					north.lockInterruptibly();
					Thread.sleep(500);
					east.lockInterruptibly();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(north.isHeldByCurrentThread()){
						north.unlock();
					}
					if(east.isHeldByCurrentThread()){
						east.unlock();
					}
				}
			}
		}

		public static void main(String[] args) {
			DeadLockCar car2north = new DeadLockCar(north);
			DeadLockCar car2east = new DeadLockCar(east);
			car2north.start();
			car2east.start();
		}
}
