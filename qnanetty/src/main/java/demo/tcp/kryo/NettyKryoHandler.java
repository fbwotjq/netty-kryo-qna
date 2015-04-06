package demo.tcp.kryo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Iterator;
import java.util.Set;

import demo.model.User;

@Sharable
public class NettyKryoHandler extends SimpleChannelInboundHandler<User>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {
		
		System.out.println(String.format("SERVER::channelRead0 : %s, %s", msg.getId(), msg.getName()));
		/*Set<String> set = msg.getHistorySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			System.out.println("history:"+iterator.next());
		}*/
		
		User aaa = new User();
		aaa.setName("name");
		aaa.setId("userId");
		System.out.println("SERVER::write message !! ");
		ctx.write(aaa);
		
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel is active\n");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("\nChannel is disconnected");
		super.channelInactive(ctx);
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
	
}
