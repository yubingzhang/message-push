package com.study.chat.common.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.study.chat.common.serialize.Serializer;
import com.study.chat.common.serialize.SerializerType;

/**
 * json序列化方式
 *
 * @author zhangyubing
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte serializeType() {
        return SerializerType.SERIALIZE_TYPE_JSON;
    }

    @Override
    public <T> byte[] serialize(T object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes,clazz);
    }
}
