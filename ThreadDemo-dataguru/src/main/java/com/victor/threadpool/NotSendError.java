package com.victor.threadpool;

import java.util.concurrent.SynchronousQueue;  
import java.util.concurrent.ThreadPoolExecutor;  
import java.util.concurrent.TimeUnit;  
  
public class NotSendError implements Runnable{  
  
    int a,b;  
    public NotSendError(int a,int b){  
        this.a = a;  
        this.b = b;  
    }  
      
    @Override  
    public void run() {  
        double re = a/b;  
        System.out.println(re);  
    }  
      
    public static void main(String[] args) {  
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0,Integer.MAX_VALUE,  
                0L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());  
        for(int i=0;i<5;i++){  
            pool.submit(new NotSendError(100, i));  
        }  
    }  
}  