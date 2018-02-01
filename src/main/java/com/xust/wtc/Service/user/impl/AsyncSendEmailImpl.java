package com.xust.wtc.Service.user.impl;

import com.xust.wtc.Entity.Result;
import com.xust.wtc.Service.user.AsyncSendEmail;
import com.xust.wtc.utils.Secret;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by Spirit on 2018/2/1.
 */
@Service
public class AsyncSendEmailImpl implements AsyncSendEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 异步邮件发送生产者
     * @param loginName
     * @param email
     * @return
     */
    @Override
    public Result emailProducer(String loginName, String email) {
        Result result = new Result();
        String message = loginName + ":" + email;
        try {
            kafkaTemplate.send("email", message);
            result.setStatus(1);
            result.setContent("发送成功，请及时登录邮箱修改密码");
        } catch (Exception e) {
            result.setStatus(0);
            result.setContent("发送失败，请稍后重试");
        }
        return result;
    }

}
