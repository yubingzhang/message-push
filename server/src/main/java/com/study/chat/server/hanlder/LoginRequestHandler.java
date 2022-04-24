package com.study.chat.server.hanlder;

import com.study.chat.common.context.Session;
import com.study.chat.common.protocol.request.LoginRequestPacket;
import com.study.chat.common.protocol.response.LoginResponsePacket;
import com.study.chat.common.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 登录请求处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if (validLogin(msg.getUserId(), msg.getPassword())) {
            loginResponsePacket.setUserId(msg.getUserId());
            loginResponsePacket.setSuccess(true);
            SessionUtil.bindSession(Session.builder().userId(msg.getUserId()).build(),ctx.channel());
        }
        else {
            loginResponsePacket.setErrorMsg("用户名或密码错误");
        }
        ctx.writeAndFlush(loginResponsePacket);
    }

    private boolean validLogin(String userId,String password) {
        // TODO 调用数据库查询用户信息
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
