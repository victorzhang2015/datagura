package com.antu.disruptor;

import com.lmax.disruptor.dsl.Disruptor;  
import com.lmax.disruptor.dsl.ProducerType;  
import com.lmax.disruptor.BlockingWaitStrategy;  
import com.lmax.disruptor.RingBuffer;  
import java.nio.ByteBuffer;  
import java.util.concurrent.Executor;  
import java.util.concurrent.Executors;  
public class LongEventMain {  
	public static void run(Object object){
		 // ִ���������ڹ����������߳�  
        Executor executor = Executors.newCachedThreadPool();  

        // ָ���¼�����  
        LongEventFactory factory = new LongEventFactory();  

        // ָ�� ring buffer�ֽڴ�С, must be power of 2.  
        int bufferSize = 1024;  

        //���߳�ģʽ����ȡ���������  
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,   
                bufferSize,executor,  
                ProducerType.SINGLE,  
                new BlockingWaitStrategy());  
        //�����¼�ҵ������---������  
        disruptor.handleEventsWith(new LongEventHandler());  
        //����disruptor�߳�  
        disruptor.start();  

        // ��ȡ ring buffer�������ڽ�ȡ�������������¼�  
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();  
        //Ϊ ring bufferָ���¼�������  
        //LongEventProducer producer = new LongEventProducer(ringBuffer);  
        LongEventProducerWithTranslator producer=new LongEventProducerWithTranslator(ringBuffer);  
        ByteBuffer bb = ByteBuffer.allocate(8);//Ԥ��8�ֽڳ������ֽڻ���  
        byte[] bb1 = object.toString().getBytes();
        bb.put(bb1);
        for (long l = 0; true; l++)  
        {  
            producer.product(bb);//��������������  
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }  
          
	}
}  