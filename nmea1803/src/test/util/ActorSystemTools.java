package test.util;
import akka.actor.ActorRef;  
import akka.actor.ActorSystem;  
import akka.actor.Props;  
  
/** 
 * 
 */  
public class ActorSystemTools {  
  
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
    
    public static void main(String[] args) {  
        ActorSystemTools.start();  
        ActorRef TCPServer = ActorSystemTools.actorOf(TCPServer.class);  
        ActorRef codemanager = ActorSystemTools.actorOf(CodecManagerForAkka.class); 
        ActorRef zeroMQ = ActorSystemTools.actorOf(ZeroMqActor.class); 
        TCPServer.tell("hello! I am  TCPServer!", codemanager);  
        ActorSystemTools.shutdown();
    }  
}  