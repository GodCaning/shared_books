package com.xust.wtc.Dao.user;

import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Spirit on 2017/12/4.
 */
@Repository
public interface UserMapper {

    int register(Person person);

    Person findUserByLoginName(@Param("loginName") String loginName);

    Person findUser(@Param("id") int id);

    int updateUserInfo(Person person);

    int modifyPassWd(@Param("name") String loginName, @Param("passwd") String passwd);
}
