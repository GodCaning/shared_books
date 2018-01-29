package com.xust.wtc.Service.user.impl;

import com.xust.wtc.Dao.user.UserMapper;
import com.xust.wtc.Entity.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.UserService;
import com.xust.wtc.utils.Secret;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Spirit on 2017/12/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Result modifyPassWd(String code, String passwd) {
        Result result = new Result();
        String para = Secret.base64Decode(code);
        String secretKey;
        String loginName;
        try {
            secretKey = para.split(":")[0];
            loginName = para.split(":")[1];
        } catch (Exception e) {
            result.setStatus(0);
            result.setContent("非法验证码");
            return result;
        }
        Long time = (Long) redisTemplate.opsForHash().get("emailExpirationTime", secretKey);
        if (time == null || ((Instant.now().getEpochSecond() - time) > 1800)) {
            result.setStatus(0);
            result.setContent("验证码失效或不存在");
            return result;
        }
        System.out.println(passwd);
        SimpleHash simpleHash =
                new SimpleHash("md5", passwd, null, 1023);
        System.out.println(simpleHash.toBase64());
        if (userMapper.modifyPassWd(loginName, simpleHash.toBase64()) > 0) {
            result.setStatus(1);
            result.setContent("修改成功");
        } else {
            result.setStatus(0);
            result.setContent("修改失败");
        }
        return result;
    }

    /**
     * 验证用户名
     * @param loginName
     */
    @Override
    public Result validationLogin(String loginName) {
        Person person = userMapper.findUserByLoginName(loginName);
        Result result = new Result();
        if (person == null) {
            result.setStatus(0);
            result.setContent("用户名不存在");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("name", person.getLoginName());
            map.put("email", person.getEmail());
            result.setStatus(1);
            result.setContent(map);
        }
        return result;
    }

    /**
     * 发送邮件
     * @param loginName
     * @param email
     * @return
     */
    @Override
    public Result sendEmail(String loginName, String email) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        Result result = new Result();
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("1033184008@qq.com");
            helper.setTo(email);
            helper.setSubject("共享图书找回密码");
            helper.setText(createEmailText(loginName), true);
        } catch (MessagingException e) {
            e.printStackTrace();
            result.setStatus(0);
            result.setContent("发送失败，请稍后重试");
        }
        mailSender.send(message);
        result.setStatus(1);
        result.setContent("发送成功，请及时登录邮箱修改密码");
        return result;
    }

    @Override
    public Result register(Person person) {
        Result result = new Result();
        SimpleHash simpleHash =
                new SimpleHash("md5", person.getLoginPasswd(), null, 1023);
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

    /**
     * 生成邮件文本
     * @param
     * @return
     */
    private String createEmailText(String loginName){

        //生成密钥
        String secretKey = UUID.randomUUID().toString();
        long now = Instant.now().getEpochSecond();

        redisTemplate.opsForHash().put("emailExpirationTime", secretKey, now);

        String para = secretKey + ":" +loginName;
        System.out.println(para);
        para = Secret.base64Encode(para);

//        HttpServletRequest request= ServletActionContext.getRequest();
//
//        String path=request.getContextPath();
//
//        String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
//
        String resetPassHref =  "http://localhost:8080/forget3.html?para=" + para;

        String emailContent = "<html><body>请勿回复本邮件.点击下面的入口,重设密码" +
                "<h3><a href='" + resetPassHref +
                "' target='_blank'>点我点我</a></h3><br/><br/><br/>" +
                "tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'</body></html>";

        return emailContent;
    }
}
