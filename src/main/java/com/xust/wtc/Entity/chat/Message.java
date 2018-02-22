package com.xust.wtc.Entity.chat;

/**
 * Created by Spirit on 2018/2/20.
 */
public class Message {

    private int sendId;   //谁发送的

    private int receiveId;   //给谁发送

    private String message;   //发送的内容

    public Message() {}

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sendId=" + sendId +
                ", receiveId=" + receiveId +
                ", message='" + message + '\'' +
                '}';
    }
}
