package com.sh.netty;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author sunhu
 * @date 2020/8/12 18:13
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector=Selector.open();
        //绑定一个端口6666，监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //注册到selector关心事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            if (selector.select(1000)==0){
                System.out.println("服务器等待1s，无连接");
                continue;
            }
            //关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //迭代器遍历 key
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //如果是OP_ACCEPT,客户端连接
                if(key.isAcceptable()){
                    SocketChannel socketChannel=serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成一个socketChannel"+socketChannel.hashCode());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                //OP_READ,发生写
                if (key.isReadable()){
                    //通过key获取到channel
                    SocketChannel channel= (SocketChannel) key.channel();
                    //获取到关联的buffer
                    ByteBuffer buffer =(ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端"+new String(buffer.array()));
                }
                //移除当前的selectionKey,防止重复操作
                keyIterator.remove();
            }

        }




    }
}
