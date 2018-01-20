package com.xust.wtc.Service.user;

import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2017/12/4.
 */
public interface FriendService {
    Result addFriend(int concernedId, int fansId);
    Result deleteFriend(int concernedId, int fansId);
    List<Person> listConcernedInfo(int id);
    List<Person> listFansInfo(int id);
}
