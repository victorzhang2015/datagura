package com.victor.threadpool;

public class Worker extends Thread {
	private ThreadPool pool;
	
	private boolean isIdle =false;
	private boolean isShutDown = false;
	private Runnable target;
	private Worker() {
		super();
	}
	
	public Worker(ThreadPool pool, String name,Runnable target) {
		super(name);
		this.pool = pool;
		this.target = target;
	}

	
	/**
	 * @return the target
	 */
	public Runnable getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public synchronized void setTarget(Runnable newtarget) {
		this.target = newtarget;
		notifyAll();
	}

	public synchronized void shutDown(){
		isShutDown =true;
		notifyAll();
	}
	public void run(){
		while(!isShutDown){
			isIdle =false;
			if(target!=null){
				target.run();
			}
			isIdle =true;
			try{
				//该认为结束后，不关闭线程，而是放入线程池中
				pool.repool(this);
				synchronized(this){
					//线程空闲，等待新的任务到来
					wait();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
