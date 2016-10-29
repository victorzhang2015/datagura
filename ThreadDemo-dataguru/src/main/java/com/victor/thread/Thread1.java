package com.victor.thread;

/**
 * Hello world!
 *
 */
public class Thread1 implements Runnable{
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        new Thread1().run();
        new Thread1().notify();
    }

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			/**Thread.interrupted();该方法判断是否被打断，并清除打断状态
			Thread.interrupted()假如当前的中断标志为true，则调完后会将中断标志位设置成false 
			Thread.currentThread().isInterrupted();该方法判断是否被打断，不清除打断状态*/
			if(Thread.currentThread().isInterrupted()){
				System.out.println("该线程被打断");
				break;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				//直接将异常抛出会清空中断状态
				// TODO Auto-generated catch block
				System.out.println("interrupted when sleep");
				Thread.currentThread().interrupt();
			}
		}
	}
}
