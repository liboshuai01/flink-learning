package cn.liboshuai.flink.netty.demo06.client;

import cn.liboshuai.flink.netty.demo06.client.console.ConsoleCommandManager;
import cn.liboshuai.flink.netty.demo06.client.console.LoginConsoleCommand;
import cn.liboshuai.flink.netty.demo06.client.handler.HeartBeatTimerHandler;
import cn.liboshuai.flink.netty.demo06.client.handler.IMResponseHandler;
import cn.liboshuai.flink.netty.demo06.client.util.LoginUtil;
import cn.liboshuai.flink.netty.demo06.common.codec.Delimiter;
import cn.liboshuai.flink.netty.demo06.common.codec.PacketCodecHandler;
import cn.liboshuai.flink.netty.demo06.common.handler.IMIdleStateHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * 客户端入口类
 */
@Slf4j
public class NettyClient {

    private static final int MAX_RETRY = 5; // 最大重连次数
    private static final String HOST = "127.0.0.1"; // 服务端地址
    private static final int PORT = 11001; // 服务端端口号

    public static void main(String[] args) {
        // 创建线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 客户端启动核心类
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(workerGroup) // 设置线程组
                .channel(NioSocketChannel.class) // 指定客户端的通道类型
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 设置连接超时时间
                .option(ChannelOption.SO_KEEPALIVE, true) // 保持长连接
                .option(ChannelOption.TCP_NODELAY, true) // { 禁用 Nagle 算法
                .handler(new ChannelInitializer<SocketChannel>() { // 配置初始化处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new IMIdleStateHandler()); // 空闲检测
                        socketChannel.pipeline().addLast(new Delimiter()); // 解决半包沾包
                        socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE); // 编码解码
                        socketChannel.pipeline().addLast(HeartBeatTimerHandler.INSTANCE); // 心跳请求
                        socketChannel.pipeline().addLast(IMResponseHandler.INSTANCE); // 处理IM指令
                    }
                });

        // 尝试连接服务端
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    // 连接服务端的方法，支持重试机制
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接至 [{}:{}] 成功！", host, port); // 连接成功日志
                Channel channel = ((ChannelFuture) future).channel();
                // 启动控制台线程监听键盘输入
                startConsoleThread(channel);
            } else if (retry == 0) {
                log.info("连接至 [{}:{}] 失败！重试次数已用完，放弃连接！", host, port); // 如果没有重试次数了，直接放弃连接
            } else {
                int order = (MAX_RETRY - retry) + 1; // 当前的重试次数
                int delay = 1 << order; // 指数退避机制计算重试延迟时间
                log.info("连接至 [{}:{}] 失败！第{}次重连", host, port, order); // 打印日志信息
                // 调度重试任务
                bootstrap.config().group().schedule(() ->
                        connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS
                );
            }
        });
    }

    /**
     * 启动控制台线程监听键盘输入
     */
    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
            LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
            while (!Thread.interrupted()) {
                if (!LoginUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
