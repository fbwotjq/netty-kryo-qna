package demo.tcp.kryo;

import demo.Client;
import demo.model.User;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ObjectClientHandler extends SimpleChannelInboundHandler<User> {

	@Override
    public void channelActive(ChannelHandlerContext ctx) {
		System.out.println("CLIENT:: channelActive N send message");
		User user = new User();
		user.setId("fbwotjq");
		user.setName("홍길동");
/*		user.getHistorySet().add("의적");
		user.getHistorySet().add("부자");
		user.getHistorySet().add("도둑");*/
		ctx.write(user);

    }
	
	@Override
	public void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {
		System.out.println(String.format("CLIENT::channelRead0 : %s, %s", msg.getId(), msg.getName()));
		User user = new User();
		user.setId("fbwotjq1");
		user.setName("홍길동1");
/*		user.getHistorySet().add("의적1");
		user.getHistorySet().add("부자1");
		user.getHistorySet().add("도둑1");*/
		if(Client.requestIndex == Client.requestEndIndex) {
			System.out.println(System.currentTimeMillis()-Client.startPoint);
			System.exit(-1);
		} else {
			Client.requestIndex++;
		}
		//System.out.println("client write");
		//ctx.write(user);
		
	}

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    	System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
    
}
