package com.study.chat.common.attribute;

import com.study.chat.common.context.Session;
import io.netty.util.AttributeKey;

/**
 * 连接相关属性
 *
 * @author zhangyubing
 */
public interface Attributes {
    /**
     * 用户会话属性
     */
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
