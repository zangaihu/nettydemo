package com.sh.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * ServerHandler
 * @author sunhu
 * @date 2020/8/19 9:09
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //服务端读取数据，msg已经经过了解码
        int len=msg.getLen();
        byte[] content=msg.getContent();

        System.out.println("服务器接收到信息如下：");
        System.out.println("长度="+len);
        System.out.println("内容=" + new String(content, StandardCharsets.UTF_8));
        System.out.println("服务器接收到消息包数量=" + (++this.count));

        //回复消息
        String responseContent= UUID.randomUUID().toString();
        int responseLen=responseContent.getBytes("UTF-8").length;
        byte[] responseContent2=responseContent.getBytes("UTF-8");
        //创建一个协议包，发送给客户端，会经过编码器进行编码
        MessageProtocol messageProtocol=new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(responseContent2);
        ctx.writeAndFlush(messageProtocol);
    }
}
