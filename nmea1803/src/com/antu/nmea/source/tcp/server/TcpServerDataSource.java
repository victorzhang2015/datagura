package com.antu.nmea.source.tcp.server;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test.util.ActorSystemToolForNetty;

import com.antu.nmea.sentence.INmeaSentence;
import com.antu.nmea.source.AbstractNmeaDataSource;
import com.antu.nmea.source.AcceptanceSetting;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class TcpServerDataSource extends AbstractNmeaDataSource {
	
	static private Log logger = LogFactory.getLog(TcpServerDataSource.class); 
	
	private TcpServerDataSourceSetting setting;
	
	private TcpServerHandler handler;
	
	private ActorSystemToolForNetty akkahandle; // 调用akka

	public TcpServerDataSource(String talker, TcpServerDataSourceSetting setting, AcceptanceSetting accSetting) {
		super(talker, accSetting);
		
		assert(setting != null);
		this.setting = setting;
		
		this.handler = new TcpServerHandler(setting, manager);
	}

	@Override
	public String getName() {
		return this.setting.getName();
	}

	@Override
	public void stop() {
	}

	@Override
	public void run() {

		do {
			EventLoopGroup g = new NioEventLoopGroup();
			
			try {
				Bootstrap bs = new Bootstrap().group(g).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true);
				bs.handler(new ChannelInitializer<SocketChannel>(){
		
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(akkahandle);
						//ch.pipeline().addLast(new ReadTimeoutHandler(15));//15毫秒无连接自己关掉
						ch.pipeline().addLast("idleStateHandler",new IdleStateHandler(2000,2000,2000,TimeUnit.MILLISECONDS));//超时重新连接
					}
					
				});
				
				ChannelFuture f = bs.connect(this.setting.getHost(), this.setting.getPort()).sync();
				TcpServerDataSource.logger.info("connected to " + this.setting.getHost() + ":" + this.setting.getPort());
				f.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				TcpServerDataSource.logger.error("exception during connected to " + this.setting.getHost() + ":" + this.setting.getPort(), e);
			} finally {
				g.shutdownGracefully();
				TcpServerDataSource.logger.info("client shutdown");
			}
		} while (this.setting.getAutoRecover());
	}

	@Override
	public void send(INmeaSentence sentence) {

		List<String> strings = this.manager.encode(talker, sentence);
		
		this.handler.send(strings);
	}

}
