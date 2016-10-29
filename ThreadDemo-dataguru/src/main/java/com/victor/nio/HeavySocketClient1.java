package com.victor.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class HeavySocketClient1 {
	private static ExecutorService tp = Executors.newCachedThreadPool();
	private static final int sleep_time =1000*1000*1000;
	public static class EchoClient implements Runnable{
		private int num1;
		private int num2;
		private String flag;
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Socket client =null;
			PrintWriter writer =null;
			BufferedReader reader =null;
			try {
				client = new Socket();
				client.connect( new InetSocketAddress("localhost",8000));
				writer = new PrintWriter(client.getOutputStream(),true);
				int sum=0;
				if("+".equals(flag)){
					sum = num1+num2;
				}else if("*".equals(flag)){
					sum = num1*num2;
				}else if("/".equals(flag)){
					sum = num1/num2;
				}else if("-".equals(flag)){
					sum = num1-num2;
				}
				writer.println(num1+" "+flag+" "+num2+" = "+sum);
				writer.flush();
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				System.out.println("from server:"+reader.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private EchoClient(int num1, int num2, String flag) {
			this.num1 = num1;
			this.num2 = num2;
			this.flag = flag;
		}
		
	}

	public static void main(String[] args) {
		System.out.println("输入需要计算的公式：");
		System.out.print("数字一：");
		Scanner scan = new Scanner(System.in);
		int num1 = scan.nextInt();
		System.out.print("数字二：");
		int num2 = scan.nextInt();
		System.out.print("运算符：");
		String flag = scan.next();
		System.out.println("输入需要计算的公式："+num1+flag+num2);
		for(int i =0;i<5;i++){
			tp.execute( new EchoClient(num1,num2,flag));
		}
	}
}

