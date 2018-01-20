package com.xust.wtc.Controller.chat;

import com.xust.wtc.Entity.chat.ChatList;
import com.xust.wtc.Entity.chat.PrivateMessage;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.chat.PrivateMessageService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Spirit on 2018/1/14.
 */
@RestController
public class PrivateMessageController {

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
    public List<PrivateMessage> findPrivateMessageBySendIdAndReceiveId(@PathVariable("id") int receiveId) {
//        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        String sessionId = "";
        List<PrivateMessage> list = privateMessageService.findPrivateMessageBySendIdAndReceiveId(sessionId, receiveId);
        for (PrivateMessage p : list) {
            System.out.println(p);
        }
        return list;
    }

    /**
     * 私聊信息  用户ID_内容
     * @param content
     */
    @MessageMapping(value = "/chat")
    public void sendMessage(String content) {
        String []strings = content.split("_");
        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        privateMessageService.insertPrivateMessage(sessionId, Integer.parseInt(strings[0]), strings[1]);
        simpMessageSendingOperations.convertAndSendToUser(strings[0], "/message", strings[1]);
    }

    /**
     * 删除私聊信息
     * @param receiveId
     * @return
     */
    @DeleteMapping(value = "/deleteMessage/{id}", consumes = "application/json", produces = "application/json")
    public Result deletePrivateMessage(@PathVariable("id") int receiveId) {
        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        return privateMessageService.deleteMessage(sessionId, receiveId);
    }

    @GetMapping(value = "/findChatList", consumes = "application/json", produces = "application/json")
    public List<ChatList> findChatList() {
//        String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
        String sessionId = "";
        return privateMessageService.findListDisplay(sessionId);
    }
}
