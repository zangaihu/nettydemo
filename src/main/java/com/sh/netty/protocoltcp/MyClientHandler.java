package com.sh.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author sunhu
 * @date 2020/8/17 19:01
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len=msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收消息如下:");
        System.out.println("长度="+len);
        System.out.println("内容" + new String(content, StandardCharsets.UTF_8));
        System.out.println("客户端接收消息数量=" + (++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i=0;i<5;i++){
            String mes="hello，i am client";
            byte[] content=mes.getBytes(StandardCharsets.UTF_8);
            int length=mes.getBytes(StandardCharsets.UTF_8).length;
            //封装成MessageProtocol，然后经过编码器编码
            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常信息=" + cause.getMessage());
        ctx.close();
    }
}
