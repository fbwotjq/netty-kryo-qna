
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.Assert;
import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import demo.model.User;
import demo.tcp.kryo.NettyKryoDecoder;
import demo.tcp.kryo.NettyKryoEncoder;

public class KryoCodecTest {

	@Test
	public void kryoEncoderTest(){

		EmbeddedChannel channel = new EmbeddedChannel(new NettyKryoEncoder());
		Kryo kryo = new Kryo(); 
		kryo.register(User.class);
		User message = new User();
		message.setId("sdfsd");
		message.setName("sdfsdf");
		Assert.assertTrue(channel.writeOutbound(message));
		
		ByteBuf encoded = (ByteBuf)channel.readOutbound();
		int size = encoded.readableBytes();
		byte[] data = new byte[size]; 
		encoded.readBytes(data);
		System.out.println(encoded.readableBytes());
		System.out.println(size);
		Input input = null;
		try {
			input = new Input(data);
			User message2 = (User) kryo.readClassAndObject(input);
			System.out.println(String.format("%s, %s", message2.getId(), message2.getName())); 
		} catch(Exception e){
			e.printStackTrace();
		} finally {  
			input.close();
		}
		
	}
	
	@Test
	public void kryoDecoderTest(){
		EmbeddedChannel channel = new EmbeddedChannel(new NettyKryoDecoder());
		Kryo kryo = new Kryo(); 
		kryo.register(User.class);
		User message = new User();
		message.setId("sdfsd");
		message.setName("sdfsdf");
		Assert.assertTrue(channel.writeInbound(message));
		User message2 = (User) channel.readInbound();
		System.out.println(String.format("%s, %s", message2.getId(), message2.getName())); 
		
		Assert.assertTrue(channel.writeInbound(message));
		User message3 = (User) channel.readInbound();
		System.out.println(String.format("%s, %s", message3.getId(), message3.getName())); 
		
	}
	
}
