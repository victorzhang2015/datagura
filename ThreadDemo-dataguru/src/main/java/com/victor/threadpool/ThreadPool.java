package com.victor.threadpool;

import java.util.List;
import java.util.Vector;

public class ThreadPool {
	private static ThreadPool instance =null;
	private List<Worker> idleThreads;
	private int threadCounter;
	private boolean isShutDown =false;
	private ThreadPool() {
		this.idleThreads=new Vector(5);
		// TODO Auto-generated constructor stub
		threadCounter =0;
	}
	public int getThreadsCount(){
		return threadCounter;
	}
	//取得线程池的实例
	public synchronized static ThreadPool getInstance(){
		if(instance == null)
			instance = new ThreadPool();
			return instance;
		
	}
	//将线程放入池中
	protected synchronized void repool(Worker repoolingThread){
		if(!isShutDown){
			idleThreads.add(repoolingThread);
		}else{
			repoolingThread.shutDown();
		}
	}
	
	public synchronized void start(Runnable target){
		Worker thread =null;
		if(idleThreads.size()>0){
			int lastIndex = idleThreads.size()-1;
			thread =(Worker)idleThreads.get(lastIndex);
			idleThreads.remove(lastIndex);
			thread.setTarget(target);
		}else{
			threadCounter++;
			//创建新线程，
			thread = new Worker(this,"Threadp"+threadCounter,target);
			thread.start();
		}
	}
}
