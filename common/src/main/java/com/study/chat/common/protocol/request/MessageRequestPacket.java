package com.study.chat.common.protocol.request;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import lombok.Data;

/**
 * 消息请求
 *
 * @author zhangyubing
 */
@Data
public class MessageRequestPacket extends Packet {
    /**
     * 消息接收用户id
     */
    private String destUserId;

    /**
     * 消息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
