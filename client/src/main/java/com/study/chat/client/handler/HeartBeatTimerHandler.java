package com.study.chat.client.handler;

import com.study.chat.common.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 定时心跳处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    private static final int HEARTBEAT_INTERVAL = 5;
    public static final HeartBeatTimerHandler INSTANCE = new HeartBeatTimerHandler();
    private HeartBeatTimerHandler() {}

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().scheduleAtFixedRate(() -> {
            if (ctx.channel().isActive()) {
                System.out.println("客户端每"+HEARTBEAT_INTERVAL+"秒写一条数据到服务端");
                ctx.writeAndFlush(new HeartBeatRequestPacket());
            }
        }, 0,HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
