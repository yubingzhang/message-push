package com.study.chat.server.hanlder;

import com.study.chat.common.protocol.Command;
import com.study.chat.common.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 聊天消息聚合处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class ImHandler extends SimpleChannelInboundHandler<Packet> {
    public static final ImHandler INSTANCE = new ImHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private ImHandler() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        handlerMap.put(Command.LOGOUT_REQUEST, LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        System.out.println("服务端聚合处理器");
        handlerMap.get(msg.getCommand()).channelRead(ctx, msg);
    }
}
