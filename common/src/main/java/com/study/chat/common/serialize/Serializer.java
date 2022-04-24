package com.study.chat.common.serialize;

import com.study.chat.common.serialize.impl.JsonSerializer;


/**
 * 序列号器
 *
 * @author zhangyubing
 */
public interface Serializer {
    /**
     * 序列化类型
     *
     * @return
     */
    byte serializeType();

    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T object);

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);

    /**
     * 默认序列化方式
     *
     * @return
     */
    Serializer DEFAULT =  new JsonSerializer();
}
