package com.study.chat.common.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * 读空闲超时处理
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class ReadIdleStateHandler extends IdleStateHandler {

    public static final ReadIdleStateHandler INSTANCE = new ReadIdleStateHandler();

    // 读空闲时间间隔
    private static final int READER_IDLE_TIME = 15;

    private ReadIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println("读空闲超时，" + READER_IDLE_TIME + "秒没有读到数据！");
        ctx.channel().close();
    }

}
