package com.xust.wtc.Entity.chat;

import java.sql.Timestamp;

/**
 * 私信
 * Created by Spirit on 2018/1/14.
 */
public class PrivateMessage {

    private int id; //私信ID

    private int sendId; //显示发送ID

    private String sendName; //显示发送名字

    private int receiveId; //显示接受ID

    private String receiveName; //显示接受名字

    private int isSend; //是否是显示ID发送的消息

    private String content; //消息

    private Timestamp timestamp; //创建时间

    public PrivateMessage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PrivateMessage{" +
                "id=" + id +
                ", sendId=" + sendId +
                ", sendName=" + sendName +
                ", receiveId=" + receiveId +
                ", receiveName=" + receiveName +
                ", isSend=" + isSend +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
