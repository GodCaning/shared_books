package com.xust.wtc.Service.user.impl;

import com.xust.wtc.Dao.user.UserMapper;
import com.xust.wtc.Entity.chat.Feedback;
import com.xust.wtc.Entity.user.Person;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.AsyncSendEmail;
import com.xust.wtc.Service.user.UserService;
import com.xust.wtc.utils.CONSTANT_STATUS;
import com.xust.wtc.utils.Secret;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Spirit on 2017/12/4.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AsyncSendEmail asyncSendEmail;

    /**
     * 修改用户头像
     * @param portrait
     * @param id
     * @return
     */
    @Override
    public Result modifyPortrait(String portrait, int id) {
        Result result = new Result();
        portrait = "http://on9arvrjb.bkt.clouddn.com/" + portrait;
        if (userMapper.modifyPortrait(portrait, id) > 0) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("修改成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("修改失败");
        }
        return result;
    }

    @Override
    public Result feedback(Feedback feedback, int id) {
        Result result = new Result();
        String content = feedback.getFeedbackContent() == null ? "" : feedback.getFeedbackContent();
        String contact = feedback.getFeedbackContact() == null ? "" : feedback.getFeedbackContact();
        if (userMapper.insertFeedback(content, contact, id) > 0) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("插入成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("插入失败");
        }
        return result;
    }

    /**
     * 修改密码
     * @param code
     * @param passwd
     * @return
     */
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
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("非法验证码");
            return result;
        }
        Long time = (Long) redisTemplate.opsForHash().get("emailExpirationTime", secretKey);
        if (time == null || ((Instant.now().getEpochSecond() - time) > 1800)) {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("验证码失效或不存在");
            return result;
        }
        System.out.println(passwd);
        SimpleHash simpleHash =
                new SimpleHash("md5", passwd, null, 1023);
        if (userMapper.modifyPassWd(loginName, simpleHash.toHex()) > 0) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("修改成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
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
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("用户名不存在");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("name", person.getLoginName());
            map.put("email", person.getEmail());
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent(map);
        }
        return result;
    }

//    /**
//     * 同步发送邮件
//     * @param loginName
//     * @param email
//     * @return
//     */
//    @Override
//    public Result sendEmail(String loginName, String email) {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper;
//        Result result = new Result();
//        try {
//            helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setFrom("1033184008@qq.com");
//            helper.setTo(email);
//            helper.setSubject("共享图书找回密码");
//            helper.setText(createEmailText(loginName), true);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            result.setStatus(CONSTANT_STATUS.ERROR);
//            result.setContent("发送失败，请稍后重试");
//        }
//        mailSender.send(message);
//        result.setStatus(CONSTANT_STATUS.SUCCESS);
//        result.setContent("发送成功，请及时登录邮箱修改密码");
//        return result;
//    }

    /**
     * 异步发送邮件
     * @param loginName
     * @param email
     * @return
     */
    @Override
    public Result sendEmail(String loginName, String email) {
        return asyncSendEmail.emailProducer(loginName, email);
    }

    @Override
    public Result register(Person person) {
        Result result = new Result();
        if (userMapper.findUserByLoginName(person.getLoginName()) != null) {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("登录名存在");
            return result;
        }
        SimpleHash simpleHash =
                new SimpleHash("md5", person.getLoginPasswd(), null, 1023);
        person.setLoginPasswd(simpleHash.toHex());
        if (userMapper.register(person) > 0) {
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("注册成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
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
            result.setStatus(CONSTANT_STATUS.SUCCESS);
            result.setContent("修改成功");
        } else {
            result.setStatus(CONSTANT_STATUS.ERROR);
            result.setContent("修改失败");
        }
        return result;
    }

//    /**
//     * 生成邮件文本
//     * @param
//     * @return
//     */
//    private String createEmailText(String loginName){
//
//        //生成密钥
//        String secretKey = UUID.randomUUID().toString();
//        long now = Instant.now().getEpochSecond();
//
//        redisTemplate.opsForHash().put("emailExpirationTime", secretKey, now);
//
//        String para = secretKey + ":" +loginName;
//        System.out.println(para);
//        para = Secret.base64Encode(para);
//
//        String resetPassHref =  "http://localhost:8080/forget3.html?para=" + para;
//
//        String emailContent = "<html><body>请勿回复本邮件.点击下面的入口,重设密码" +
//                "<h3><a href='" + resetPassHref +
//                "' target='_blank'>点我点我</a></h3><br/><br/><br/>" +
//                "tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'</body></html>";
//
//        return emailContent;
//    }
}
