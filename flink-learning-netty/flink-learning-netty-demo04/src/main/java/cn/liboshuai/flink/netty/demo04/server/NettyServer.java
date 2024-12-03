package cn.liboshuai.flink.netty.demo04.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端启动入口类
 */
@Slf4j
public class NettyServer {

    // 定义服务端默认监听的端口号
    private static final int PORT = 11001;

    public static void main(String[] args) {
        // 创建用于接受客户端连接的主线程组（boosGroup）
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        // 创建用于处理客户端 I/O 事件的工作线程组（workerGroup）
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 实例化 Netty 的服务端引导类
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                .group(boosGroup, workerGroup) // 绑定主（boss）和工作线程组
                .channel(NioServerSocketChannel.class) // 指定服务端通道类型为 NioServerSocketChannel (非阻塞 IO)
                .option(ChannelOption.SO_BACKLOG, 1024) // 设置服务端 TCP 连接的队列大小
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持 TCP 连接的活动状态
                .childOption(ChannelOption.TCP_NODELAY, true) // 禁用 Nagle 算法，降低网络延迟
                // 配置子处理器，处理具体的客户端数据
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 添加处理器到通道管道中，处理业务逻辑
                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
                    }
                });

        // 尝试绑定指定端口
        bind(serverBootstrap, PORT);
    }

    /**
     * 递归绑定端口，若绑定失败则递增端口号后重试
     */
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port) // 绑定端口
                .addListener(future -> { // 添加异步操作完成监听器
                    if (future.isSuccess()) {
                        log.info("端口 [{}] 绑定成功！", port); // 绑定成功的日志记录
                    } else {
                        log.info("端口 [{}] 绑定失败！", port); // 绑定失败的日志记录
                        bind(serverBootstrap, port + 1); // 如果绑定失败，递增端口号后重新绑定
                    }
                });
    }
}
