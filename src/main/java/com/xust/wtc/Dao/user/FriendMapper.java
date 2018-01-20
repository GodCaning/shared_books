package com.xust.wtc.Dao.user;

import com.xust.wtc.Entity.Person;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2017/12/4.
 */
@Repository
public interface FriendMapper {
    /**
     * 建立粉丝和偶像之间的关系
     * @param concernedId
     * @param fansId
     * @return
     */
    int addFriend(@Param("concernedId") int concernedId, @Param("fansId") int fansId);

    /**
     * 删除关系
     * @param concernedId
     * @param fansId
     * @return
     */
    int deleteFriend(@Param("concernedId") int concernedId, @Param("fansId") int fansId);

    /**
     * 根据ID找到关注的人
     * @param id
     * @return
     */
    List<Person> listConcernedInfo(@Param("id") int id);

    /**
     * 根据ID找到粉丝
     * @param id
     * @return
     */
    List<Person> listFansInfo(@Param("id") int id);
}
