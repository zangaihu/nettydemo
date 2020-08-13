package com.sh.BIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sunhu
 * @date 2020/8/10 18:49
 */
public class BIOServer {
    public static void main(String[] args) throws Exception{
        //线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        //一个服务端socket
        final ServerSocket serverSocket=new ServerSocket(6666);

        while (true){
            //有服务端连接,创建一个线程去处理
            final Socket socket = serverSocket.accept();
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static  void handler(Socket socket){

        byte[] bytes=new byte[1024];

        try {
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
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
