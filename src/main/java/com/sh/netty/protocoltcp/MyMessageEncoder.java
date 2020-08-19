package com.sh.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import sun.plugin2.message.Message;

/**
 * 编码器，将数据长度和内容编码，对应解码
 * @author sunhu
 * @date 2020/8/17 19:15
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyMessageEncoder encode 方法被调用");
        //数据长度写入int
        out.writeInt(msg.getLen());
        //数据内容写入bytes
        out.writeBytes(msg.getContent());
    }
}
