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
			/**Thread.interrupted();�÷����ж��Ƿ񱻴�ϣ���������״̬
			Thread.interrupted()���統ǰ���жϱ�־Ϊtrue��������Ὣ�жϱ�־λ���ó�false 
			Thread.currentThread().isInterrupted();�÷����ж��Ƿ񱻴�ϣ���������״̬*/
			if(Thread.currentThread().isInterrupted()){
				System.out.println("���̱߳����");
				break;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				//ֱ�ӽ��쳣�׳�������ж�״̬
				// TODO Auto-generated catch block
				System.out.println("interrupted when sleep");
				Thread.currentThread().interrupt();
			}
		}
	}
}
