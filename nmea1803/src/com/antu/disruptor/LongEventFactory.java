package com.antu.disruptor;

import com.lmax.disruptor.EventFactory;  
/** 
 * 事件生产工厂 
 * 
 */  
public class LongEventFactory implements EventFactory<LongEvent>  
{  
  
    public LongEvent newInstance() {  
        return new LongEvent();  
    }  
  
}  