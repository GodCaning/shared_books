package com.xust.wtc.Service.user;

import com.xust.wtc.Entity.chat.Feedback;
import com.xust.wtc.Entity.user.Person;
import com.xust.wtc.Entity.Result;

/**
 * Created by Spirit on 2017/12/4.
 */
public interface UserService {
    Result validationLogin(String loginName);

    Result register(Person person);

    Person findUserByLoginName(String loginName);

    Person findUser(int id);

    Result updateUserInfo(Person person);

    Result sendEmail(String loginName, String email);

    Result modifyPassWd(String code, String passwd);

    Result modifyPortrait(String portrait, int id);

    Result feedback(Feedback feedback, int id);
}
