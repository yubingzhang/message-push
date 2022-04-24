package com.study.chat.client.runner;

import com.study.chat.client.handler.HeartBeatTimerHandler;
import com.study.chat.client.handler.LoginResponseHandler;
import com.study.chat.common.codec.PacketCodec;
import com.study.chat.common.codec.decode.LengthSplitDecode;
import com.study.chat.common.handler.ReadIdleStateHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 开启客户端连接
 *
 * @author zhangyubing
 */
@Slf4j
@Component
public class NettyClient implements ApplicationRunner {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8082;
    private static final int MAX_RETRY = 5;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 读空闲超时处理器
                        ch.pipeline().addLast(ReadIdleStateHandler.INSTANCE);
                        // 定长解码器
                        ch.pipeline().addLast(new LengthSplitDecode());
                        // 消息编解码器
                        ch.pipeline().addLast(PacketCodec.INSTANCE);
                        // 其他业务处理器
                        ch.pipeline().addLast(new LoginResponseHandler());


                        // 定时心跳（放最后，上面都是读操作，该处理器需要写数据，
                        // 如果放读处理器上面导致读处理器不会被处理）
                        ch.pipeline().addLast(HeartBeatTimerHandler.INSTANCE);
                    }
                });
        connect(bootstrap,MAX_RETRY);
    }

    /**
     * 连接服务器
     *
     * @param bootstrap
     * @param retry
     */
    private static void connect(Bootstrap bootstrap, int retry) {
        bootstrap.connect(HOST, PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                Channel channel = ((ChannelFuture) future).channel();
                // TODO 通过连接发送业务请求
            } else if (retry == 0) {
                System.out.println("重试次数已达最大值，请售后再试");
            } else {
                // 第几次重试
                int num = (MAX_RETRY - retry) + 1;
                bootstrap.config().group().schedule(() -> connect(bootstrap, retry - 1), 1 << num, TimeUnit.SECONDS);
            }
        });
    }
}
