package test.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.antu.nmea.codec.CodecManager;
import com.antu.nmea.codec.exception.SentenceCodecNotFoundException;
import com.antu.nmea.codec.exception.SentenceFieldCodecNotFoundException;
public class TCPServer extends UntypedActor{
	private ActorRef  server;
	private ActorRef  manager;
	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("tcpserver receive message : " + (String)message);  
		server=getSender();  
		manager =getSelf();
		// TODO Auto-generated method stub
		ServerSocket serverSocket = new ServerSocket(6789);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		ConnectSocket thread = new ConnectSocket(serverSocket);
		while(true) {
			fixedThreadPool.execute(thread);
		}
	}
	class ConnectSocket implements Runnable{
		private ServerSocket serverSocket;
		private BufferedReader inFromClient;
		private DataOutputStream outToClient;
		ConnectSocket(ServerSocket serverSocket) {
			super();
			this.serverSocket = serverSocket;
		}
	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Socket connectionSocket;
			try {
				connectionSocket = serverSocket.accept();
				inFromClient=new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				// handle incoming connection from connectionSocket
				System.out.println("connection is complete!");
				if(inFromClient!=null){
					String code = inFromClient.readLine();
					server.tell(code, manager);  
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("connection is wrong!");
			}
			
		}
		
	}
}