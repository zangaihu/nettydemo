package com.sh.BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 *  BIO Server
 * @author sunhu
 * @date 2020/8/10 18:49
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(512),new ThreadPoolExecutor.DiscardPolicy());
        //一个服务端socket
        final ServerSocket serverSocket=new ServerSocket(6666);

        while (true){
            //有服务端连接,创建一个线程去处理
            final Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> handler(socket));
        }
    }

    public static  void handler(Socket socket){
        byte[] bytes=new byte[1024];
        try {
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            //输出
            while (true){
                int read = inputStream.read(bytes);
                if (read!=-1){
                    System.out.println(new String(bytes,0,read));
                }else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
