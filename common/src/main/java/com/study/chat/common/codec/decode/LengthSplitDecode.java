package com.study.chat.common.codec.decode;

import com.study.chat.common.constants.CommonConstant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 定长解码器
 *
 * @author zhangyubing
 */
public class LengthSplitDecode extends LengthFieldBasedFrameDecoder {

    /**
     * 长度域字段偏移量
     */
    public static final int LENGTH_FIELD_OFFSET = 7;

    /**
     * 长度域字段长度
     */
    public static final int LENGTH_FIELD_LENGTH = 4;

    /**
     * 继承父类解码逻辑
     */
    public LengthSplitDecode() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != CommonConstant.MAGIC_NUM) {
            // 关闭连接
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
