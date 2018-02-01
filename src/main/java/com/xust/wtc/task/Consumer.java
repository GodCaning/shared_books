package com.xust.wtc.task;

import com.xust.wtc.utils.Secret;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.util.UUID;

/**
 * kafka异步接受需要发送的邮件并处理
 * Created by Spirit on 2018/2/1.
 */
@Component
public class Consumer {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RedisTemplate redisTemplate;

    @KafkaListener(topics = {"email"})
    public void emailConsumer(ConsumerRecord<?, ?> record) {
        System.out.println("kafka的value: " + record.value().toString());

        String message = record.value().toString();
        System.out.println(message);
        String loginName = message.split(":")[0];
        String email = message.split(":")[1];
        System.out.println(loginName + email);

        MimeMessage emailMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(emailMessage, true, "UTF-8");
            helper.setFrom("1033184008@qq.com");
            helper.setTo(email);
            helper.setSubject("共享图书找回密码");
            helper.setText(createEmailText(loginName), true);
            mailSender.send(emailMessage);
            System.out.println("发送成功");
        } catch (MessagingException e) {

        }
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

        String resetPassHref =  "http://localhost:8080/forget3.html?para=" + para;

        String emailContent = "<html><body>请勿回复本邮件.点击下面的入口,重设密码" +
                "<h3><a href='" + resetPassHref +
                "' target='_blank'>点我点我</a></h3><br/><br/><br/>" +
                "tips:本邮件超过30分钟,链接将会失效，需要重新申请'找回密码'</body></html>";

        return emailContent;
    }
}
