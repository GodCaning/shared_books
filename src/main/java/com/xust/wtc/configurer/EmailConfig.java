package com.xust.wtc.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件配置类
 * Created by Spirit on 2018/1/26.
 */
@Configuration
public class EmailConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //设置邮件服务主机
        mailSender.setHost("smtp.qq.com");
        //设置邮件服务端口
        mailSender.setPort(587);
        //发送者邮箱的用户名
        mailSender.setUsername("1033184008@qq.com");
        //发送者邮箱的密码
        mailSender.setPassword("rnoyjwjazrpnbchc");
        return mailSender;
    }
}
