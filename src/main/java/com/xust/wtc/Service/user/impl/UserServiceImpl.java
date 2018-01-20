package com.xust.wtc.Service.user.impl;

import com.xust.wtc.Dao.user.UserMapper;
import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Spirit on 2017/12/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result register(Person person) {
        Result result = new Result();
        SimpleHash simpleHash =
                new SimpleHash("md5", person.getLoginPasswd(), null, 1024);
        person.setLoginPasswd(simpleHash.toBase64());
        if (userMapper.register(person) > 0) {
            result.setStatus(1);
            result.setContent("注册成功");
        } else {
            result.setStatus(0);
            result.setContent("注册失败");
        }
        return result;
    }

    @Override
    public Person findUserByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }

    /**
     * 根据ID获取用户信息
     * @param id
     * @return
     */
    @Override
    public Person findUser(int id) {
        return userMapper.findUser(id);
    }

    /**
     * 根据条件修改用户信息
     * @param person
     * @return
     */
    @Override
    public Result updateUserInfo(Person person) {
        Result result = new Result();
        if (userMapper.updateUserInfo(person) > 0) {
            result.setStatus(1);
            result.setContent("修改成功");
        } else {
            result.setStatus(0);
            result.setContent("修改失败");
        }
        return result;
    }
}
