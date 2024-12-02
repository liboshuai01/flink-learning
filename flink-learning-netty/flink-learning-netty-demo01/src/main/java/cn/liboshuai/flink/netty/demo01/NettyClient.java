package cn.liboshuai.flink.netty.demo01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    // 定义客户端的最大重试次数
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        // 创建客户端主线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 用于启动客户端的 Bootstrap 类
        Bootstrap bootstrap = new Bootstrap();

        bootstrap
                // 设置线程组
                .group(workerGroup)
                // 指定客户端使用 NioSocketChannel
                .channel(NioSocketChannel.class)
                // 设置客户端自定义属性
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                // 配置客户端的 TCP 参数
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 设置连接超时时间
                .option(ChannelOption.SO_KEEPALIVE, true) // 开启 TCP Keep-Alive 保持连接
                .option(ChannelOption.TCP_NODELAY, true) // 关闭 TCP 的 Nagle 算法
                // 设置客户端的处理 Pipeline
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 空实现，可在此处添加自定义业务逻辑
                    }
                });

        // 尝试连接服务端
        connect(bootstrap, "localhost", 11000, MAX_RETRY);
    }

    /**
     * 客户端连接的逻辑，并处理重试
     *
     * @param bootstrap 客户端的 Bootstrap 实例
     * @param host      服务端地址
     * @param port      服务端端口
     * @param retry     剩余重试次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                // 如果连接成功，打印日志
                log.info("连接至 [{}:{}] 成功！", host, port);
            } else if (retry == 0) {
                // 如果用完所有重试次数，打印日志，放弃重连
                log.info("连接至 [{}:{}] 失败！重试次数已用完，放弃连接！", host, port);
            } else {
                // 如果连接失败但仍有剩余重试次数，计划重试连接
                int order = (MAX_RETRY - retry) + 1; // 第几次重试
                int delay = 1 << order; // 每次重试的等待时间指数增加，类似于指数退避算法
                log.info("连接至 [{}:{}] 失败！第{}次重连", host, port, order);

                // 调度在 delay 秒后重试连接
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
