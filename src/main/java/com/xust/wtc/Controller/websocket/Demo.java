package com.xust.wtc.Controller.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Spirit on 2017/12/3.
 */
@RestController
public class Demo {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping(value = "/chat1")
    public void a(String s) {
        System.out.println(s);
        simpMessageSendingOperations.convertAndSendToUser("2","/message", "huifu");
//        simpMessageSendingOperations.convertAndSend("/topic", "huifu");
    }

    public static void main(String[] args) {
        System.out.println("/user/"+
                StringUtils.replace("2", "/", "%2F")
        + "/message");
    }
}
