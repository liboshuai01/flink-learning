package cn.liboshuai.flink.netty.demo05.server;

import cn.liboshuai.flink.netty.demo05.codec.Delimiter;
import cn.liboshuai.flink.netty.demo05.codec.PacketDecoder;
import cn.liboshuai.flink.netty.demo05.codec.PacketEncoder;
import cn.liboshuai.flink.netty.demo05.server.handler.AuthHandler;
import cn.liboshuai.flink.netty.demo05.server.handler.LoginRequestHandler;
import cn.liboshuai.flink.netty.demo05.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端入口类
 */
@Slf4j
public class NettyServer {

    private static final int PORT = 11001; // 服务端默认监听的端口号

    public static void main(String[] args) {
        // 创建两个线程组，一个用于处理接收的连接（bossGroup），另一个处理具体工作（workerGroup）
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 启动服务器的核心类
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 配置服务器启动类的各种参数
        serverBootstrap
                .group(boosGroup, workerGroup) // 设置线程组
                .channel(NioServerSocketChannel.class) // 指定服务端的通道类型
                .option(ChannelOption.SO_BACKLOG, 1024) // 指定连接队列大小
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 保持长连接
                .childOption(ChannelOption.TCP_NODELAY, true) // 禁用 Nagle 算法，减少延迟
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 初始化每一个客户端连接的处理器
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new Delimiter()); // 添加自定义的 Delimiter
                        nioSocketChannel.pipeline().addLast(new PacketDecoder()); // 添加自定义的 PacketDecoder
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler()); // 添加自定义的 LoginRequestHandler
                        nioSocketChannel.pipeline().addLast(new AuthHandler()); // 添加自定义的 AuthHandler
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler()); // 添加自定义的 MessageRequestHandler
                        nioSocketChannel.pipeline().addLast(new PacketEncoder()); // 添加自定义的 PacketEncoder
                    }
                });

        // 绑定端口并启动监听
        bind(serverBootstrap, PORT);
    }

    // 绑定端口的方法，自动递增端口号以避免端口被占用
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port)
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("端口 [{}] 绑定成功！", port); // 绑定成功的日志
                    } else {
                        log.info("端口 [{}] 绑定失败！尝试绑定下一个端口...", port); // 绑定失败的日志
                        bind(serverBootstrap, port + 1); // 递增端口号，重试绑定
                    }
                });
    }
}
