package com.mtime.springboot.test.netty;
import java.io.UnsupportedEncodingException;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter  {

	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println(">>>channelRead()");
		
		ByteBuf buf = (ByteBuf) msg;
		String recieved = getMessage(buf);
		System.out.println("服务器接收到消息：" + recieved);
		try {
			ctx.writeAndFlush(getSendByteBuf("APPLE"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 从ByteBuf中获取信息 使用UTF-8编码返回
	 */
	private String getMessage(ByteBuf buf) {
		System.out.println(">>>getMessage()");
		
		byte[] con = new byte[buf.readableBytes()];
		buf.readBytes(con);
		try {
			return new String(con, Constant.UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private ByteBuf getSendByteBuf(String message)throws UnsupportedEncodingException {
		System.out.println(">>>getSendByteBuf()");

		byte[] req = message.getBytes("UTF-8");
		ByteBuf pingMessage = Unpooled.buffer();
		pingMessage.writeBytes(req);

		return pingMessage;
	}
}