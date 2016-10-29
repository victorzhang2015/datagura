package com.victor.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MutiThreadNIOServer {
	private static ExecutorService tp = Executors.newFixedThreadPool(10);
	public static Map<Socket,Long> getm_time_stat = new HashMap<Socket,Long>();
	class EchoClient{
		private LinkedList<ByteBuffer> outo;
		EchoClient(){
			outo = new LinkedList<ByteBuffer>();
		}
		public LinkedList<ByteBuffer> getOutputQueue(){
			return outo;
		}
		
		public void enqueue(ByteBuffer bb){
			outo.addFirst(bb);
		}
	}
	class HandleMsg implements Runnable{
		SelectionKey sk;
		ByteBuffer bb;
		private HandleMsg(SelectionKey sk, ByteBuffer bb) {
			super();
			this.sk = sk;
			this.bb = bb;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			EchoClient echoClient =(EchoClient)sk.attachment();
			echoClient.enqueue(bb);
			sk.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
			//强迫selector立即返回
			selector.wakeup();
		}
	
	}
	private Selector selector;
	public void startServer() throws Exception{
		selector = SelectorProvider.provider().openSelector();
		ServerSocketChannel ssc=ServerSocketChannel.open();
		ssc.configureBlocking(false);//设置为非阻塞
		InetSocketAddress isa = new InetSocketAddress(8000);
		ssc.socket().bind(isa);
		SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
		for(;;){
			selector.select();//阻塞状态，直到某一组值满足条件
			Set<SelectionKey> readkeys =selector.selectedKeys();
			Iterator<SelectionKey> i = readkeys.iterator();
			long e = 0;
			while(i.hasNext()){
				SelectionKey sk = (SelectionKey)i.next();
				i.remove();
				if(sk.isAcceptable()){
					doAccept(sk);
				}else if(sk.isValid()&&sk.isReadable()){
					if(!getm_time_stat.containsKey(((SocketChannel)sk.channel()).socket())){
						getm_time_stat.put(((SocketChannel)sk.channel()).socket(), System.currentTimeMillis());
					}
					doRead(sk);
				}else if(sk.isValid()&&sk.isWritable()){
					doWrite(sk);
					e=System.currentTimeMillis();
					long b = getm_time_stat.remove(((SocketChannel)sk.channel()).socket());
					System.out.println("spend"+(e-b)+"ms");
				}
			}
		}
	}
	
	public void doAccept(SelectionKey sk){
		ServerSocketChannel server =(ServerSocketChannel)sk.channel();
		SocketChannel clientChannel;
		try {
			clientChannel=server.accept();//获取一个selectionkey
			clientChannel.configureBlocking(false);
			SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
			EchoClient echoClient = new EchoClient();
			clientKey.attach(echoClient);//视作一个附件附在key上
			InetAddress clientAddress = clientChannel.socket().getInetAddress();
			System.out.println("accept the selection from "+clientAddress.getHostAddress());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("failed to accept new client");
			e.printStackTrace();
		}
	}
	
	public void doRead(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		ByteBuffer bb = ByteBuffer.allocateDirect(8192);
		int len;
		try{
			len =channel.read(bb);
			if(len<0){
				disconnect(sk);
				return;
			}
		}catch(Exception e){
			System.out.println("fail connect from client");
			e.printStackTrace();
			disconnect(sk);
			return;
		}
		bb.flip();
		tp.execute( new HandleMsg(sk,bb));
	}
	
	
	public void doWrite(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		EchoClient echoclient =(EchoClient)sk.attachment();
		LinkedList<ByteBuffer> outq = echoclient.getOutputQueue();
		ByteBuffer bb = outq.getLast();
		try{
			int len =channel.write(bb);
			if(len==-1){
				disconnect(sk);
				return;
			}
			if(bb.remaining()==0){
				outq.removeLast();
			}
		}catch(Exception e){
			System.out.println("failed to write to client");
			e.printStackTrace();
			disconnect(sk);
		}
		if(outq.size()==0){
			sk.interestOps(SelectionKey.OP_READ);
		}
	}
	
	public void disconnect(SelectionKey sk){
		SocketChannel channel = (SocketChannel)sk.channel();
		try {
			System.out.println(channel.getLocalAddress()+": disconnect");
			channel.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		MutiThreadNIOServer echoserver = new MutiThreadNIOServer();
		try {
			echoserver.startServer();
		} catch (Exception e) {
			System.out.println("Exception caught,program exiting.....");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
