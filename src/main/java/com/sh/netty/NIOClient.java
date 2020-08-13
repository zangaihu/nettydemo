package com.sh.netty;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author sunhu
 * @date 2020/8/12 19:01
 */
public class NIOClient {
    public static void main(String[] args)throws Exception {

        //创建一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666 );
        //连接服务器,未成功时
        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("未完成连接...");
            }
        }
        //连接成功，发送数据
        String str="hello,world";
        ByteBuffer buffer=ByteBuffer.wrap(str.getBytes());
        //发送数据，将buffer数据写入channel
        socketChannel.write(buffer);
        System.in.read();

    }
}
