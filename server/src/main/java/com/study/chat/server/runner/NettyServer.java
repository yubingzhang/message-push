package com.study.chat.server.runner;

import com.study.chat.common.codec.PacketCodec;
import com.study.chat.common.codec.decode.LengthSplitDecode;
import com.study.chat.server.hanlder.HeartBeatRequestHandler;
import com.study.chat.common.handler.ReadIdleStateHandler;
import com.study.chat.server.hanlder.AuthHandler;
import com.study.chat.server.hanlder.ImHandler;
import com.study.chat.server.hanlder.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动，开启端口监听
 *
 * @author zhangyubing
 */
@Slf4j
@Component
public class NettyServer implements ApplicationRunner {

    private static final int PORT = 8082;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 读空闲超时处理器
                        ch.pipeline().addLast(ReadIdleStateHandler.INSTANCE);
                        // 定长解码器
                        ch.pipeline().addLast(new LengthSplitDecode());
                        // 消息编解码器
                        ch.pipeline().addLast(PacketCodec.INSTANCE);
                        // 登录请求处理器
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        // 心跳请求处理器
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        // 认证处理器
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        // 优化性能，减少handler链路及对象内存消耗，聚合同类的handler
                        ch.pipeline().addLast(ImHandler.INSTANCE);
                    }
                });
        serverBootstrap.bind(PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务启动成功");
            } else {
                System.out.println("服务启动失败");
            }
        });
    }
}
