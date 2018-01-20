package com.xust.wtc.Dao.chat;

import com.xust.wtc.Entity.chat.ChatList;
import com.xust.wtc.Entity.chat.PrivateMessage;
import com.xust.wtc.Entity.chat.UnreadCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2018/1/14.
 */
@Repository
public interface PrivateMessageMapper {

    /**
     * 查询私聊
     * @param sendId
     * @param receiveId
     * @return
     */
    List<PrivateMessage> findPrivateMessageBySendIdAndReceiveId(@Param("sendId") int sendId, @Param("receiveId") int receiveId);

    /**
     * 更新未读情况
     * @param sendId
     * @param receiveId
     * @return
     */
    int updateUnreadCount(@Param("sendId") int sendId, @Param("receiveId") int receiveId);

    /**
     * 插入发送方的聊天记录
     * @param sendId
     * @param receiveId
     * @param content
     * @return
     */
    int insertSend(@Param("sendId")int sendId, @Param("receiveId")int receiveId, @Param("content")String content);

    /**
     * 插入接受方的聊天记录
     * @param sendId
     * @param receiveId
     * @param content
     * @return
     */
    int insertReceive(@Param("sendId")int sendId, @Param("receiveId")int receiveId, @Param("content")String content);

    /**
     * 删除聊天记录
     * @param sendId
     * @param receiveId
     * @return
     */
    int deletePrivateMessage(@Param("sendId")int sendId, @Param("receiveId")int receiveId);

    /**
     * 查询列表显示情况
     * @param sendId
     * @return
     */
    List<ChatList> findListDisplay(@Param("sendId")int sendId);

    /**
     * 未读数目查询
     * @param sendId
     * @return
     */
    List<UnreadCount> findUnreadCount(@Param("sendId")int sendId);
}
