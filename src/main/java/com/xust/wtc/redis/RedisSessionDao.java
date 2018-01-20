package com.xust.wtc.redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * shiro session
 * Created by Spirit on 2017/11/26.
 */
@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    private static final String SESSION_PREFIX = "WTC_SHIRO_SESSION:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:" + sessionId);

        redisTemplate.opsForValue().set(SESSION_PREFIX + sessionId.toString(), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:" + sessionId);
        Session session = (Session) redisTemplate.opsForValue().get(SESSION_PREFIX + sessionId.toString());
        return session;
    }

    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.debug("更新session:" + session.getId());
        String key = SESSION_PREFIX + session.getId().toString();
        redisTemplate.opsForValue().set(key, session);
    }

    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        logger.debug("删除session:" + session.getId());
        redisTemplate.delete(SESSION_PREFIX + session.getId().toString());
    }
}
