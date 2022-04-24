package com.study.chat.common.protocol.response;


import com.study.chat.common.protocol.Packet;

import static com.study.chat.common.protocol.Command.HEARTBEAT_RESPONSE;

/**
 * 心跳响应
 *
 * @author zhangyubing
 */
public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
