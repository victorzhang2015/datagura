package com.antu.disruptor;

import com.lmax.disruptor.EventFactory;  
/** 
 * �¼��������� 
 * 
 */  
public class LongEventFactory implements EventFactory<LongEvent>  
{  
  
    public LongEvent newInstance() {  
        return new LongEvent();  
    }  
  
}  