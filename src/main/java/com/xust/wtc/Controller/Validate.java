package com.xust.wtc.Controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Spirit on 2017/12/2.
 */
@RestController
public class Validate {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/wangcan")
    public void a(HttpServletRequest request, HttpServletResponse response, String code) {
        String s = String.valueOf(redisTemplate.opsForValue().get(code));
        System.out.println(s);
        SecurityUtils.getSubject().getSession();
        Subject subject = new Subject.Builder().sessionId(code).buildSubject();
        System.out.println(subject.getPrincipal());
    }

    @GetMapping("/ttt")
    public void s(HttpSession session) {
        System.out.println("----------------->" + session.getId());
        Object o = new Subject.Builder().sessionId(session.getId()).buildSubject().getPrincipals();
        System.out.println(o);
    }

    @GetMapping("/shibai")
    public void s1(Session session) {
        System.out.println("---------shibai-------->" + session.getId());
    }
}
