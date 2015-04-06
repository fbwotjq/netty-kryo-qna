package demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import demo.tcp.kryo.NettyKryoProtocolInitalizer;

public class Server {

	static NettyKryoProtocolInitalizer nettyKryoProtocolInitalizer = new NettyKryoProtocolInitalizer();
	static NioEventLoopGroup bossNioEventLoopGroup = new NioEventLoopGroup();
	static NioEventLoopGroup workerNioEventLoopGroup = new NioEventLoopGroup();
	static Channel serverChannel = null;
	
	public static void main(String[] args) {
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossNioEventLoopGroup, workerNioEventLoopGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new LoggingHandler(LogLevel.DEBUG))
				.childHandler(nettyKryoProtocolInitalizer);
		
		try {
			System.out.println("server-start");
			serverChannel = serverBootstrap.bind(8080).sync().channel().closeFuture().sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossNioEventLoopGroup.shutdownGracefully();
			workerNioEventLoopGroup.shutdownGracefully();
		}
				
	}
	
}
