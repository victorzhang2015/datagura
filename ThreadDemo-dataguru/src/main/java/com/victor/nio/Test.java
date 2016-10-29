package com.victor.nio;

import java.util.Scanner;

public class Test {
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
		
	}
}
