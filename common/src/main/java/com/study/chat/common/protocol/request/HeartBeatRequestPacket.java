package com.study.chat.common.protocol.request;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;

/**
 * 心跳请求
 *
 * @author zhangyubing
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
