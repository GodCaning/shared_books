package com.xust.wtc.Service.user;

import com.xust.wtc.Entity.Result;

/**
 * Created by Spirit on 2018/2/1.
 */
public interface AsyncSendEmail {

    Result emailProducer(String loginName, String email);
}
