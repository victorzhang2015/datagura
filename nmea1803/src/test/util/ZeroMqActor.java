package test.util;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class ZeroMqActor  extends UntypedActor {
	private ActorRef  server;
	private ActorRef  manager;
	@Override
	public void onReceive(Object message) throws Exception {
		server=getSender();  
		manager =getSelf();
		// TODO Auto-generated method stub
		String msg = (String) message;
		byte[] arr =msg.getBytes();
		// TODO Auto-generated method stub
		 Context context = ZMQ.context(1);
	        Socket publisher = context.socket(ZMQ.PUB);
	        publisher.bind("tcp://*:5563");
	        while (!Thread.currentThread ().isInterrupted ()) {
	            // Write two messages, each with an envelope and content
	            publisher.sendMore (arr);
	        }
	        publisher.close ();
	        context.term ();
	     
	}

}
