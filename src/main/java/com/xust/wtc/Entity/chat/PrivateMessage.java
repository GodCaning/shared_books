package com.xust.wtc.Entity.chat;

/**
 * 私信
 * Created by Spirit on 2018/1/14.
 */
public class PrivateMessage {

    private int id; //私信ID

    private int sendId; //显示发送ID

    private String sendName; //显示发送名字

    private String sendPortrait; //显示发送头像

    private int receiveId; //显示接受ID

    private String receiveName; //显示接受名字

    private String receivePortrait; //显示接受头像

    private int isSend; //是否是显示ID发送的消息

    private String message; //消息

    private String displayDateTime; //创建时间

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

    public String getSendPortrait() {
        return sendPortrait;
    }

    public void setSendPortrait(String sendPortrait) {
        this.sendPortrait = sendPortrait;
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

    public String getReceivePortrait() {
        return receivePortrait;
    }

    public void setReceivePortrait(String receivePortrait) {
        this.receivePortrait = receivePortrait;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDisplayDateTime() {
        return displayDateTime;
    }

    public void setDisplayDateTime(String displayDateTime) {
        this.displayDateTime = displayDateTime;
    }
}
