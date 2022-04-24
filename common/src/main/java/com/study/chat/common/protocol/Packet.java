package com.study.chat.common.protocol;

import lombok.Data;

/**
 * 协议包体
 *
 * @author zhangyubing
 */
@Data
public abstract class Packet {
    /**
     * 协议版本号
     */
    private Byte version = 1;

    /**
     * 协议对应的操作命令
     * @return
     */
    public abstract Byte getCommand();
}
