package com.sh.netty.protocoltcp;

/**
 * 消息体
 * @author sunhu
 * @date 2020/8/17 18:58
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
