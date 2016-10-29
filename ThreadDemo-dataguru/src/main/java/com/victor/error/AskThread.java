package com.victor.error;

import java.util.concurrent.CompletableFuture;

public class AskThread implements Runnable {
	CompletableFuture<Integer> re =null;
	
	
	
	public AskThread(CompletableFuture<Integer> re) {
		super();
		this.re = re;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		int myRe=0;
		try{
			myRe=re.get()*re.get();
		}catch(Exception e){
			
		}
		System.out.println(myRe);
	}
	public static void main(String[] args) throws InterruptedException {
		final CompletableFuture<Integer> future = new CompletableFuture<Integer>();
		new Thread(new AskThread(future)).start();
		//模拟长时间的计算过程
		Thread.sleep(1000);
		//告知完成结果
		future.complete(60);
	}
}
