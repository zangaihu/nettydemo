package com.sh.netty.httpdemo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author sunhu
 * @date 2020/8/14 15:22
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化channel，加入处理器
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //得到管道
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("MyHttServerCodec",new HttpServerCodec());
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
    }
}
