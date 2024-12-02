package cn.liboshuai.flink.netty.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    // 定义服务端监听的初始端口号
    private static final int BEGIN_PORT = 11000;

    public static void main(String[] args) {
        // 创建两个线程组：boosGroup 接收连接，workerGroup 处理连接
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // ServerBootstrap 用于启动服务端
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 定义一个 AttributeKey，标识客户端连接的额外信息
        final AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap
                // 设置线程组
                .group(boosGroup, workerGroup)
                // 指定服务端使用 NioServerSocketChannel
                .channel(NioServerSocketChannel.class)
                // 添加一个处理服务端启动时事件的 handler
                .handler(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                    }
                })
                // 给服务端绑定自定义属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                // 给每个子 channel（客户端连接）绑定自定义属性
                .childAttr(clientKey, "clientValue")
                // 配置服务端的 TCP 参数
                .option(ChannelOption.SO_BACKLOG, 1024) // 设置全连接队列的大小
                // 配置客户端连接的 TCP 参数
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 开启 TCP Keep-Alive 保持连接
                .childOption(ChannelOption.TCP_NODELAY, true) // 关闭 TCP 的 Nagle 算法，提高实时性
                // 设置子 channel 的 Pipeline 初始化逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        // 打印子 channel 的属性
                        log.info("clientKey: {}", nioSocketChannel.attr(clientKey).get());
                    }
                });

        // 绑定端口，监听连接
        bind(serverBootstrap, BEGIN_PORT);
    }

    // 绑定端口的逻辑，会在绑定失败时进行递增端口号的重试
    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                // 如果绑定成功，打印日志
                log.info("端口 [{}] 绑定成功！", port);
            } else {
                // 如果绑定失败，打印日志，并尝试绑定下一个端口
                log.info("端口 [{}] 绑定失败！", port);
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
