package com.study.chat.server.hanlder;

import com.alibaba.fastjson.JSON;
import com.study.chat.common.protocol.request.HeartBeatRequestPacket;
import com.study.chat.common.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 心跳请求处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        System.out.println("服务端读到心跳数据:"+ JSON.toJSONString(requestPacket));
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
