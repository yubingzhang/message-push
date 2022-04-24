package com.study.chat.server.hanlder;

import com.study.chat.common.context.Session;
import com.study.chat.common.protocol.request.MessageRequestPacket;
import com.study.chat.common.protocol.response.MessageResponsePacket;
import com.study.chat.common.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 消息处理器
 *
 * @author zhangyubing
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // 发送方会话
        Session fromSession = SessionUtil.getSession(ctx.channel());
        // 接收方channel
        Channel channel = SessionUtil.getChannel(msg.getDestUserId());
        if (channel != null && SessionUtil.hasLogin(channel)) {
            // 消息接收方消息体
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .message(msg.getMessage())
                    .fromUserId(fromSession.getUserId())
                    .build();
            channel.writeAndFlush(messageResponsePacket).addListener(future -> {
                if (future.isDone()) {
                    System.out.println("消息发送成功");
                }
            });
        }
        else {
            // TODO 没有在线，暂存消息（保存消息上下文），登录上线时判断是否有消息待发送
        }
    }
}
