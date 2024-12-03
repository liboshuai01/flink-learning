package cn.liboshuai.flink.netty.demo2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 客户端启动入口类
 */
@Slf4j
public class NettyClient {

    private static final int MAX_RETRY = 5; // 最大重试次数
    private static final String HOST = "127.0.0.1"; // 服务端地址
    private static final int PORT = 11001; // 默认的服务端端口号

    public static void main(String[] args) {
        // 创建用于处理客户端 I/O 事件的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 实例化 Netty 的客户端引导类
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                .group(workerGroup) // 设置线程组
                .channel(NioSocketChannel.class) // 指定客户端通道类型为 NioSocketChannel
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 设置连接超时时间为 5 秒
                .option(ChannelOption.SO_KEEPALIVE, true) // 设置保持 TCP 连接的活动状态
                .option(ChannelOption.TCP_NODELAY, true) // 禁用 Nagle 算法，降低网络延迟
                .handler(new ChannelInitializer<SocketChannel>() { // 配置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 添加处理器以处理客户端业务逻辑
                        socketChannel.pipeline().addLast(new FirstClientHandler());
                    }
                });

        // 尝试连接服务端，若失败则进行重试
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    /**
     * 递归实现服务端连接的重试逻辑
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接至 [{}:{}] 成功！", host, port); // 连接成功的日志记录
            } else if (retry == 0) {
                log.info("连接至 [{}:{}] 失败！重试次数已用完，放弃连接！", host, port); // 放弃连接的日志记录
            } else {
                int order = (MAX_RETRY - retry) + 1; // 计算当前重试的顺序
                int delay = 1 << order; // 定义指数回退的延迟时间
                log.info("连接至 [{}:{}] 失败！第{}次重连", host, port, order); // 记录失败重连的日志

                // 延迟一段时间后执行下一次重连
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
