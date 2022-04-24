package com.study.chat.common.protocol.request;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import lombok.Data;

/**
 * 登录请求
 * @author zhangyubing
 */
@Data
public class LoginRequestPacket extends Packet {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户密码
     */
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
