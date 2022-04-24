package com.study.chat.common.protocol.response;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import lombok.Builder;
import lombok.Data;

/**
 * @author Carbon 2019
 */
@Data
@Builder
public class LogoutResponsePacket extends Packet {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
