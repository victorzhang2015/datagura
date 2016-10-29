package com.victor.JDKTool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentQueueTest {  
    private static int COUNT = 100000;  
    private static int THREAD_NUM = 10;  
    private static CyclicBarrierThread cyclicBarrierThread = new CyclicBarrierThread();  
  
    private static ConcurrentLinkedQueue conQueue = new ConcurrentLinkedQueue();  
    private static LinkedBlockingQueue linkQueue = new LinkedBlockingQueue();  
  
    static class ConcurrentLinkedQueueProducer extends Test {  
  
        public ConcurrentLinkedQueueProducer(String id, CyclicBarrier barrier,  
                long count, int threadNum, ExecutorService executor) {  
            super(id, barrier, count, threadNum, executor);  
        }  
  
        @Override  
        protected void test() {  
            conQueue.add(1);  
        }  
    }  
  
    static class LinkedBlockingQueueProducer extends Test {  
  
        public LinkedBlockingQueueProducer(String id, CyclicBarrier barrier,  
                long count, int threadNum, ExecutorService executor) {  
            super(id, barrier, count, threadNum, executor);  
        }  
  
        @Override  
        protected void test() {  
        	linkQueue.add(1);  
        }  
    }  
  
    static class CyclicBarrierThread extends Thread {  
        @Override  
        public void run() {  
            conQueue.clear();  
            linkQueue.clear();  
        }  
    }  
  
    public static void test(String id, long count, int threadNum,  
            ExecutorService executor) {  
  
        final CyclicBarrier barrier = new CyclicBarrier(threadNum + 1,  
                cyclicBarrierThread);  
  
        System.out.println("==============================");  
        System.out.println("count = " + count + "/t" + "Thread Count = " + threadNum);  
  
        concurrentTotalTime += new ConcurrentLinkedQueueProducer("ConcurrentLinkedQueueProducer", barrier, COUNT, threadNum,executor).startTest();  
        linkedBlockingTotalTime += new LinkedBlockingQueueProducer("LinkedBlockingQueueProducer ", barrier, COUNT, threadNum,executor).startTest();  
        totalThreadCount += threadNum;  
        executor.shutdownNow();  
  
        System.out.println("==============================");  
    }  
  
    static long concurrentTotalTime = 0;  
    static long linkedBlockingTotalTime = 0;  
  
    static long totalThreadCount = 0;  
  
    public static void main(String[] args) throws InterruptedException {  
        for (int i = 1; i < 20; i++) {  
            ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM  
                    * i);  
            test("", COUNT, 10 * i, executor);  
        }  
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        
        list.get(1);
        System.out.println("ConcurrentLinkedQueue Avg Time = "  
                + concurrentTotalTime / totalThreadCount);  
  
        System.out.println("LinkedBlockingQueue Avg Time = "  
                + linkedBlockingTotalTime / totalThreadCount);  
    }  
}  