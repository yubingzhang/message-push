package com.study.chat.server.hanlder;

import com.study.chat.common.protocol.request.LogoutRequestPacket;
import com.study.chat.common.protocol.response.LogoutResponsePacket;
import com.study.chat.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 退出登录请求处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = LogoutResponsePacket.builder()
                .success(true)
                .build();
        ctx.writeAndFlush(logoutResponsePacket);
    }
}
