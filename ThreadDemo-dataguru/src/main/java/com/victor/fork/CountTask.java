package com.victor.fork;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD =100;
	private long start;
	private long end;
	@Override
	
	protected Long compute() {
		// TODO Auto-generated method stub
		long sum=0;
		boolean canCompute =(end-start)<THRESHOLD;
		if(canCompute){
			for(long i=start;i<=end;i++){
				sum+=i;
			}
		}else{
			long step=(start+end)/100;
			ArrayList<CountTask> subTasks = new ArrayList<CountTask>();
			long pos =start;
			for(int i=0;i<100;i++){
				long lastOne =pos+step;
				if(lastOne>end)lastOne =end;
				CountTask subTask = new CountTask(pos,lastOne);
				pos+=step+1;
				subTasks.add(subTask);
				subTask.fork();
			}
			for(CountTask t:subTasks){
				sum+=t.join();
			}
		}
		return sum;
	}
	private CountTask(long start, long end) {
		this.start = start;
		this.end = end;
	}
	public static void main(String[] args) {
		ForkJoinPool forkjoinPool = new ForkJoinPool();
		CountTask task = new CountTask(0,200L);
		ForkJoinTask<Long> result = forkjoinPool.submit(task);
		try {
			long res = result.get();
			System.out.println("sum="+res);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
