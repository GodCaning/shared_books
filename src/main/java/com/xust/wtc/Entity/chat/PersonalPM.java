package com.xust.wtc.Entity.chat;

import java.util.List;

/**
 * Created by Spirit on 2018/5/6.
 */
public class PersonalPM {

    private String receiveName; //显示接受名字

    private List<PrivateMessage> privateMessageList;//私聊信息显示

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public List<PrivateMessage> getPrivateMessageList() {
        return privateMessageList;
    }

    public void setPrivateMessageList(List<PrivateMessage> privateMessageList) {
        this.privateMessageList = privateMessageList;
    }

    @Override
    public String toString() {
        return "PersonalPM{" +
                "receiveName='" + receiveName + '\'' +
                ", privateMessageList=" + privateMessageList +
                '}';
    }
}
