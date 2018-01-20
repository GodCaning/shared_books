package com.xust.wtc.Entity.chat;

/**
 * 未读信息
 * Created by Spirit on 2018/1/15.
 */
public class UnreadCount {

    private int receiveId;

    private int number;

    public UnreadCount() {
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
