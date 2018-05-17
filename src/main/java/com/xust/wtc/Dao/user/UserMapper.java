package com.xust.wtc.Dao.user;

import com.xust.wtc.Entity.user.Person;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Spirit on 2017/12/4.
 */
@Repository
public interface UserMapper {

    /**
     * 注册
     * @param person
     * @return
     */
    int register(Person person);

    /**
     * 登录时查询
     * @param loginName
     * @return
     */
    Person findUserByLoginName(@Param("loginName") String loginName);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Person findUser(@Param("id") int id);

    /**
     * 更新用户信息
     * @param person
     * @return
     */
    int updateUserInfo(Person person);

    /**
     * 修改用户密码
     * @param loginName
     * @param passwd
     * @return
     */
    int modifyPassWd(@Param("name") String loginName, @Param("passwd") String passwd);

    /**
     * 修改用户头像
     * @param portrait
     * @param id
     * @return
     */
    int modifyPortrait(@Param("portrait") String portrait, @Param("id") int id);

    int insertFeedback(@Param("feedbackContent") String feedbackContent, @Param("feedbackContact") String feedbackContact, @Param("id") int id);
}
