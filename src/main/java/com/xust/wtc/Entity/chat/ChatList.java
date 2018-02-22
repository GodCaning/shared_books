package com.xust.wtc.Entity.chat;

/**
 * 聊天列表信息显示
 * Created by Spirit on 2018/1/15.
 */
public class ChatList {

    private int receiveId;

    private String receiveName;

    private String portrait;

    private int unreadCount;

    public ChatList() {
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

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    @Override
    public String toString() {
        return "ChatList{" +
                "receiveId=" + receiveId +
                ", receiveName='" + receiveName + '\'' +
                ", unreadCount=" + unreadCount +
                '}';
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
