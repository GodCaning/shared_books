package com.xust.wtc.Controller;

import com.xust.wtc.jcaptcha.JCaptcha;
import com.xust.wtc.redis.RedisSessionDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Spirit on 2017/12/2.
 */
@RestController
public class Validate {

    @Autowired
    private RedisSessionDao redisSessionDao;

    @RequestMapping("/a")
    public String a() {
        return "OK";
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        System.out.println(redisSessionDao);
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("weikom");
        } else {
            System.out.println(session.getId());
        }

        Subject subject = SecurityUtils.getSubject();
        Session session1 = subject.getSession(false);
        System.out.println(session1.getId());

        String id = request.getRequestedSessionId();
        BufferedImage bi = JCaptcha.captchaService.getImageChallengeForID(id);

        ServletOutputStream out = response.getOutputStream();

        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }
}
