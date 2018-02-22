package com.xust.wtc.Service.chat;

import com.xust.wtc.Entity.chat.ChatList;
import com.xust.wtc.Entity.chat.PrivateMessage;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2018/1/14.
 */
public interface PrivateMessageService {

    List<PrivateMessage> findPrivateMessageBySendIdAndReceiveId(int userId, int receiveId);

    Result insertPrivateMessage(int userId, int receiveId, String content);

    Result deleteMessage(int userId, int receiveId);

    List<ChatList> findListDisplay(int userId);
}
