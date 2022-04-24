package com.study.chat.common.protocol.request;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;

/**
 * 退出登录请求
 *
 * @author zhangyubing
 */
public class LogoutRequestPacket extends Packet {

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
