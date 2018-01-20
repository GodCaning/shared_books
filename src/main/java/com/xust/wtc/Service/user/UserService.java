package com.xust.wtc.Service.user;

import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;

/**
 * Created by Spirit on 2017/12/4.
 */
public interface UserService {
    Result register(Person person);

    Person findUserByLoginName(String loginName);

    Person findUser(int id);

    Result updateUserInfo(Person person);
}
