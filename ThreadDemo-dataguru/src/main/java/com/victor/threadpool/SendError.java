package com.victor.threadpool;


import java.util.concurrent.Future;  
import java.util.concurrent.SynchronousQueue;  
import java.util.concurrent.ThreadPoolExecutor;  
import java.util.concurrent.TimeUnit;  
  
public class SendError implements Runnable{  
  
    int a,b;  
    public SendError(int a,int b){  
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
                0L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>()){  
  
                    @Override  
                    public void execute(Runnable command) {  
                          
                        Runnable newCommand = wrap(command,clientTrace(),Thread.currentThread().getName());  
                        super.execute(newCommand);  
                    }  
  
                    @Override  
                    public Future<?> submit(Runnable task) {  
                        Runnable newTask = wrap(task,clientTrace(),Thread.currentThread().getName());  
                        return super.submit(newTask);  
                    }  
                      
                    private Runnable wrap(final Runnable task, final Exception clientStack,  
                            String clientThreadName){  
                          
                        return new Runnable(){  
                            @Override  
                            public void run() {  
                                try{  
                                    task.run();  
                                }catch(Exception e){  
                                    e.printStackTrace();  
                                }  
                            }  
                        }; //return”Ôæ‰Ω· ¯  
                    }  
                      
                    private Exception clientTrace(){  
                        return new Exception("Client stack trace");  
                    }  
              
        };  
        for(int i=0;i<5;i++){  
            pool.execute(new SendError(100, i));  
        }  
    }  
} 