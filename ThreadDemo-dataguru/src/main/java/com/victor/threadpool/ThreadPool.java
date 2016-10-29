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
	//ȡ���̳߳ص�ʵ��
	public synchronized static ThreadPool getInstance(){
		if(instance == null)
			instance = new ThreadPool();
			return instance;
		
	}
	//���̷߳������
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
			//�������̣߳�
			thread = new Worker(this,"Threadp"+threadCounter,target);
			thread.start();
		}
	}
}
