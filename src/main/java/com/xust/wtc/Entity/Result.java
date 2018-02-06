package com.xust.wtc.Entity;

import com.xust.wtc.utils.CONSTANT_STATUS;

/**
 * Created by Spirit on 2017/12/4.
 */
public class Result {
    private int status;
    private Object content;

    public int getStatus() {
        return status;
    }

    public void setStatus(CONSTANT_STATUS status) {
        this.status = status.getValue();
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
