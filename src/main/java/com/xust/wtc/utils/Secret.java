package com.xust.wtc.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Base64;

/**
 * Created by Spirit on 2018/1/26.
 */
public class Secret {

    private Secret(){}

    /**
     * 进行base64加密
     * @param string
     * @return
     */
    public static String base64Encode(String string) {
        Base64.Encoder encoder = Base64.getEncoder();

        byte[] textByte = null;
        try {
            textByte = string.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedText = encoder.encodeToString(textByte);
        System.out.println(encodedText);
        return encodedText;
    }

    /**
     * 解除base64
     * @param string
     * @return
     */
    public static String base64Decode(String string) {
        Base64.Decoder decoder = Base64.getDecoder();
        String result = null;
        try {
            result = new String(decoder.decode(string), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
