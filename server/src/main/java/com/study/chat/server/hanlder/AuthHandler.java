package com.study.chat.server.hanlder;

import com.study.chat.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 认证处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            ctx.pipeline().remove(this);
            // 触发处理器往下流转
            super.channelRead(ctx, msg);
        }else {
             System.out.println("没有登录，关闭连接");
             ctx.channel().close();
        }
    }
}
