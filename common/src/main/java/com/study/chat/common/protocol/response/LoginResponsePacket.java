package com.study.chat.common.protocol.response;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import lombok.Builder;
import lombok.Data;

/**
 * 登录响应
 *
 * @author zhangyubing
 */
@Data
public class LoginResponsePacket extends Packet {
    /**
     * 用户id
     */
    private String userId;

    /**
     * 登录是否成功
     */
    private boolean success;

    /**
     * 登录失败原因
     */
    private String errorMsg;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
