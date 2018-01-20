package com.xust.wtc.Service.chat.impl;

import com.xust.wtc.Dao.chat.PrivateMessageMapper;
import com.xust.wtc.Entity.chat.ChatList;
import com.xust.wtc.Entity.chat.PrivateMessage;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.chat.UnreadCount;
import com.xust.wtc.Service.chat.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Spirit on 2018/1/14.
 */
@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    @Autowired
    private PrivateMessageMapper privateMessageMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 读取一个具体的聊天信息
     * @param sessionId
     * @param receiveId
     * @return
     */
    @Override
    public List<PrivateMessage> findPrivateMessageBySendIdAndReceiveId(String sessionId, int receiveId) {
//        int sendId = (int) redisTemplate.opsForValue().get(sessionId);
        int sendId = 1;
        //更新用户未读情况
        privateMessageMapper.updateUnreadCount(sendId, receiveId);
        return privateMessageMapper.findPrivateMessageBySendIdAndReceiveId(sendId, receiveId);
    }

    /**
     * 插入私聊
     * @return
     */
    public Result insertPrivateMessage(String sessionId, int receiveId, String content) {
        Result result = new Result();
        int sendId = (int) redisTemplate.opsForValue().get(sessionId);
        int x = privateMessageMapper.insertSend(sendId, receiveId, content);
        int y = privateMessageMapper.insertReceive(sendId, receiveId, content);
        if ((x + y) < 1) {
            result.setStatus(0);
            result.setContent("发送失败");
        } else {
            result.setStatus(1);
            result.setContent("发送成功");
        }
        return result;
    }

    /**
     * 删除聊天记录
     * @param sessionId
     * @param receiveId
     * @return
     */
    @Override
    public Result deleteMessage(String sessionId, int receiveId) {
        Result result = new Result();
        int sendId = (int) redisTemplate.opsForValue().get(sessionId);
        if (privateMessageMapper.deletePrivateMessage(sendId, receiveId) > 0) {
            result.setStatus(1);
            result.setContent("删除成功");
        } else {
            result.setStatus(0);
            result.setContent("删除失败");
        }
        return result;
    }

    /**
     * 显示聊天列表
     * @param sessionId
     * @return
     */
    @Override
    public List<ChatList> findListDisplay(String sessionId) {
//        int sendId = (int) redisTemplate.opsForValue().get(sessionId);
        int sendId = 1;
        //读取该用户所有未删除的聊天框
        List<ChatList> chatLists = privateMessageMapper.findListDisplay(sendId);
        //读入该用户未读取的条数
        List<UnreadCount> unreadCountLists = privateMessageMapper.findUnreadCount(sendId);
        Map<Integer, Integer> unreadCountMaps = new HashMap<>();
        //转换为HashMap
        for (UnreadCount unreadCount : unreadCountLists) {
            unreadCountMaps.put(unreadCount.getReceiveId(), unreadCount.getNumber());
        }
        //存入未删除的聊天框中返回给前端
        for (ChatList chat : chatLists) {
            if (unreadCountMaps.get(chat.getReceiveId()) != null) {
                chat.setUnreadCount(unreadCountMaps.get(chat.getReceiveId()));
            }
        }
        return chatLists;
    }
}
