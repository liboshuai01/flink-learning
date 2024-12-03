package cn.liboshuai.flink.netty.demo06.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 服务端的自定义处理类，扩展自 Netty 的 ChannelInboundHandlerAdapter 类
 */
@Slf4j
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded - 逻辑处理器被添加");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelRegistered - channel 绑定到 NioEventLoop 线程");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive - channel 准备就绪");
        super.channelActive(ctx);
    }

    /**
     * 当读取到客户端数据时触发此方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead - channel 有数据可读");

        ByteBuf byteBuf = (ByteBuf) msg; // 将接收到的数据转换为 Netty 的 ByteBuf 类型
        log.info("接收到来自客户端的数据：{}", byteBuf.toString(StandardCharsets.UTF_8)); // 打印接收的数据（字符串）
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channelReadComplete - channel 某次数据读完");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive - channel 被关闭");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channelUnregistered - channel 被取消与 NioEventLoop 线程的绑定");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved - 逻辑处理器被移除");
        super.handlerRemoved(ctx);
    }
}
