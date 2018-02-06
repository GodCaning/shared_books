package com.xust.wtc.utils;

import org.springframework.http.HttpStatus;

/**
 * Created by Spirit on 2018/2/6.
 */
public enum CONSTANT_STATUS {
    ERROR(0),SUCCESS(1);

    private final int value;

    CONSTANT_STATUS(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
