package com.study.chat.common.protocol.response;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import lombok.Builder;
import lombok.Data;

/**
 * 聊天消息响应（转发消息到目的端）
 *
 * @author zhangyubing
 */
@Data
@Builder
public class MessageResponsePacket extends Packet {
    /**
     * 发送放用户id
     */
    private String fromUserId;

    /**
     * 消息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
