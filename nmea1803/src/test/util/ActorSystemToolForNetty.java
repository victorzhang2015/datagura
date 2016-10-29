package test.util;
import io.netty.channel.ChannelInboundHandlerAdapter;
import akka.actor.ActorRef;  
import akka.actor.ActorSystem;  
import akka.actor.Props;  
  
/** 
 * 
 */  
public class ActorSystemToolForNetty extends ChannelInboundHandlerAdapter {  
  
    private static ActorSystem actorSystem = null;  //´´½¨actorSystem
      
    public static void start() {  
        System.out.println("start actorSystem...");  
        actorSystem = ActorSystem.create();  
    }  
      
    @SuppressWarnings("unchecked")  
    public static ActorRef actorOf(Class clazz) {  
        return actorSystem.actorOf(Props.create(clazz));  
    }  
      
    public static void shutdown() {  
        System.out.println("shutdown actorSystem...");  
        actorSystem.shutdown();  
    }  
}  