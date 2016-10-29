package com.victor.error;

import java.util.concurrent.locks.StampedLock;

public class Point {
	private double x,y;
	private final StampedLock sl = new StampedLock();
	void move(double deltaX,double deltaY){
		long stamp = sl.writeLock();
		try{
			x+=deltaX;
			y+=deltaY;
		}finally{
			sl.unlockWrite(stamp);
		}
	}
	
	double distanceFromOrigin(){
		long stamp =sl.tryOptimisticRead();
		double currentX =x,currentY=y;
		if(){
			
		}
	}
}
