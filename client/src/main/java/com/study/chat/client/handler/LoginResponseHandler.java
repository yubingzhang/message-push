package com.study.chat.client.handler;

import com.study.chat.common.context.Session;
import com.study.chat.common.protocol.request.LoginRequestPacket;
import com.study.chat.common.protocol.response.LoginResponsePacket;
import com.study.chat.common.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登录响应处理器
 *
 * @author zhangyubing
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println("登录成功");
            SessionUtil.bindSession(Session.builder().userId(msg.getUserId()).build(),ctx.channel());
        }else {
            System.out.println("登录失败："+msg.getErrorMsg());
        }
    }
}
