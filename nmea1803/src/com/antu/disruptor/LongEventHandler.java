package com.antu.disruptor;
import com.lmax.disruptor.EventHandler;  
/** 
 * ��Ϣ���¼�����������ӡ���������̨ 
 * @author harry 
 * 
 */  
public class LongEventHandler  implements EventHandler<LongEvent>{  
      public void onEvent(LongEvent event, long sequence, boolean endOfBatch)  
        {  
            System.out.println("consumer:"+Thread.currentThread().getName()+" Event: value=" + event.getValue()+",sequence="+sequence+",endOfBatch="+endOfBatch);  
        }  
} 