package com.sh.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author sunhu
 * @date 2020/8/5 10:25
 */
public class NettyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGruop = new NioEventLoopGroup();


        try{
            //服务端的启动对象
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup, workGruop)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            //自定义一个处理器
                            channel.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            System.out.println("服务器ready。。。。。");
            //绑定端口并同步，生成一个ChannelFuture对象
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();
            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGruop.shutdownGracefully();
        }
    }
}
