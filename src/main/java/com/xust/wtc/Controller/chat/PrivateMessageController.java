package com.xust.wtc.Controller.chat;

import com.xust.wtc.Entity.chat.ChatList;
import com.xust.wtc.Entity.chat.Message;
import com.xust.wtc.Entity.chat.PersonalPM;
import com.xust.wtc.Entity.chat.PrivateMessage;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.chat.PrivateMessageService;
import com.xust.wtc.Service.user.UserService;
import com.xust.wtc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Spirit on 2018/1/14.
 */
@RestController
public class PrivateMessageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PrivateMessageService privateMessageService;

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * 查询私聊信息
     * @param receiveId
     * @return
     */
    @GetMapping(value = "/findMessage/{id}", consumes = "application/json", produces = "application/json")
    public PersonalPM findPrivateMessageBySendIdAndReceiveId(@PathVariable("id") int receiveId, HttpSession session) {
        PersonalPM personalPM = new PersonalPM();
        int userId = Utils.getUserId(session.getId());
        personalPM.setPrivateMessageList(privateMessageService.findPrivateMessageBySendIdAndReceiveId(userId, receiveId));
        personalPM.setReceiveName(userService.findUser(receiveId).getName());
        return personalPM;
    }

    /**
     * 私聊信息  用户ID_内容
     * @param message
     */
    @MessageMapping(value = "/chat")
    public void sendMessage(Message message) {
        privateMessageService.insertPrivateMessage(message.getSendId(), message.getReceiveId(), message.getMessage());
        simpMessageSendingOperations.convertAndSendToUser(String.valueOf(message.getReceiveId()), "/message", message);
    }

    /**
     * 删除私聊信息
     * @param receiveId
     * @return
     */
    @DeleteMapping(value = "/deleteMessage/{id}", consumes = "application/json", produces = "application/json")
    public Result deletePrivateMessage(@PathVariable("id") int receiveId, HttpSession session) {
        int userId = Utils.getUserId(session.getId());
        return privateMessageService.deleteMessage(userId, receiveId);
    }

    /**
     * 返回当前用户的全部消息列表
     * @param session
     * @return
     */
    @GetMapping(value = "/findChatList", consumes = "application/json", produces = "application/json")
    public List<ChatList> findChatList(HttpSession session) {
        int userId = Utils.getUserId(session.getId());
        return privateMessageService.findListDisplay(userId);
    }
}
