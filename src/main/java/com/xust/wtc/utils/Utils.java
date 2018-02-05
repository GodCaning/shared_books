package com.xust.wtc.utils;


import org.apache.shiro.subject.Subject;

/**
 *
 * 用户安全相关的类
 *
 * Created by Spirit on 2018/2/5.
 */
public class Utils {

    private Utils(){}

    /**
     * 根据sessionID获取登录的Subject
     * @param sessionID
     * @return
     */
    public static Subject getUserSubject(String sessionID) {
        return new Subject.Builder().sessionId(sessionID).buildSubject();
    }

    /**
     * 根据sessionID获取登录的Subject的id
     * @param sessionID
     * @return
     */
    public static int getUserId(String sessionID) {
        return (int) new Subject.Builder().sessionId(sessionID).buildSubject().getPrincipal();
    }
}
