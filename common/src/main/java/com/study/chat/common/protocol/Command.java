package com.study.chat.common.protocol;

/**
 * 协议命令
 *
 * @author zhangyubing
 */
public interface Command {
    /**
     * 登录请求命令
     */
    Byte LOGIN_REQUEST = 1;

    /**
     * 登录响应命令
     */
    Byte LOGIN_RESPONSE = 2;

    /**
     * 心跳请求命令
     */
    Byte HEARTBEAT_REQUEST = 3;

    /**
     * 心跳响应命令
     */
    Byte HEARTBEAT_RESPONSE = 4;

    /**
     * 消息请求
     */
    Byte MESSAGE_REQUEST = 5;

    /**
     * 消息响应
     */
    Byte MESSAGE_RESPONSE = 6;

    /**
     * 退出登录请求
     */
    Byte LOGOUT_REQUEST = 7;

    /**
     * 退出登录响应
     */
    Byte LOGOUT_RESPONSE = 8;
}
