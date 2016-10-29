package com.antu.disruptor;
import com.lmax.disruptor.EventHandler;  
/** 
 * 消息者事件处理器，打印输出到控制台 
 * @author harry 
 * 
 */  
public class LongEventHandler  implements EventHandler<LongEvent>{  
      public void onEvent(LongEvent event, long sequence, boolean endOfBatch)  
        {  
            System.out.println("consumer:"+Thread.currentThread().getName()+" Event: value=" + event.getValue()+",sequence="+sequence+",endOfBatch="+endOfBatch);  
        }  
} 