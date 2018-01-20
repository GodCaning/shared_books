package com.xust.wtc.Service.chat;

import com.xust.wtc.Entity.chat.ChatList;
import com.xust.wtc.Entity.chat.PrivateMessage;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2018/1/14.
 */
public interface PrivateMessageService {

    List<PrivateMessage> findPrivateMessageBySendIdAndReceiveId(String sessionId, int receiveId);

    Result insertPrivateMessage(String sessionId, int receiveId, String content);

    Result deleteMessage(String sessionId, int receiveId);

    List<ChatList> findListDisplay(String sessionId);
}
