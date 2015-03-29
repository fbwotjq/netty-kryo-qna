package demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import demo.tcp.kryo.NettyKryoDecoder;
import demo.tcp.kryo.NettyKryoEncoder;
import demo.tcp.kryo.ObjectClientHandler;

public class Client {

	static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
    
    public static void main(String[] args) {
		
    	EventLoopGroup group = new NioEventLoopGroup();
    	
        try {
        	
            Bootstrap b = new Bootstrap();
            b.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(new NettyKryoDecoder());
                     p.addLast(new NettyKryoEncoder());
                     p.addLast(new ObjectClientHandler());
                 }
             });

            System.out.println("client-start");
            ChannelFuture f = b.connect(HOST, PORT).sync();
            f.channel().closeFuture().sync();
            
        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            group.shutdownGracefully();
        }
    	
	}
}
