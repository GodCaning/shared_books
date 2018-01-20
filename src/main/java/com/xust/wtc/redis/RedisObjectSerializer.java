package com.xust.wtc.redis;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Value序列化类
 * Created by Spirit on 2017/11/26.
 */
public class RedisObjectSerializer implements RedisSerializer<Object> {

    private Converter<Object, byte[]> serializer = new SerializingConverter();

    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    static final byte[] EMPTY_ARRAY = new byte[0];

    private boolean isEmpty(byte []data) {
        return (data == null || data.length == 0);
    }

    @Override
    public byte[] serialize(Object o) {
        if (o == null) {
            return EMPTY_ARRAY;
        }
        return serializer.convert(o);
    }

    @Override
    public Object deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        return deserializer.convert(bytes);
    }
}
