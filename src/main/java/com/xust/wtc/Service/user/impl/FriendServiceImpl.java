package com.xust.wtc.Service.user.impl;

import com.xust.wtc.Dao.user.FriendMapper;
import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.FriendService;
import com.xust.wtc.utils.CONSTANT_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Spirit on 2017/12/4.
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public Result addFriend(int concernedId, int fansId) {
        Result result = new Result();
        if (friendMapper.addFriend(concernedId, fansId) > 0) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("添加成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("添加失败");
        }
        return result;
    }

    @Override
    public Result deleteFriend(int concernedId, int fansId) {
        Result result = new Result();
        if (friendMapper.deleteFriend(concernedId, fansId) > 0) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("删除成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("删除失败");
        }
        return result;
    }

    /**
     * 根据ID获取关注的人信息
     * @param id
     * @return
     */
    @Override
    public List<Person> listConcernedInfo(int id) {
        return friendMapper.listConcernedInfo(id);
    }

    /**
     * 根据ID获取粉丝信息
     * @param id
     * @return
     */
    @Override
    public List<Person> listFansInfo(int id) {
        return friendMapper.listFansInfo(id);
    }
}
