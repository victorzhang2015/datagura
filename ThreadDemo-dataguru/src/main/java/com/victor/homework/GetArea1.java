package com.victor.homework;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
public class GetArea1 extends RecursiveTask<Double> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double start=1;
	private final double end=100;
	@Override
	
	protected Double compute() {
		// TODO Auto-generated method stub
		double sum=0;
		boolean canCompute =true;
			double j =start;
			while(canCompute){
				double i =ArithUtil.add(j,0.01);
				j=i;
				double y =ArithUtil.div(1, i);
				double z=(ArithUtil.mul(y, 0.01));
				sum = ArithUtil.add(sum, z);
				if(j>100){
					canCompute=false;
				}
			}
		return sum;
	}
	
	public static void main(String[] args) {
		ForkJoinPool forkjoinPool = new ForkJoinPool(5);
		GetArea1 task = new GetArea1();
		ForkJoinTask<Double> result = forkjoinPool.submit(task);
		try {
			Double res = result.get();
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
