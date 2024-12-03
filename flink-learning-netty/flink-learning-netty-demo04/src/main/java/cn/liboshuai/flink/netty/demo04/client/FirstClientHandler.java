package cn.liboshuai.flink.netty.demo04.client;

import cn.liboshuai.flink.netty.demo04.util.NettyUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * 客户端的自定义处理类，扩展自 Netty 的 ChannelInboundHandlerAdapter 类
 */
@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端连接建立成功后触发此方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String message = "你好，我是客户端"; // 客户端向服务端发送的初始消息
        ByteBuf byteBuf = NettyUtils.convertString2ByteBuf(message, ctx); // 将字符串消息转换为 ByteBuf
        ctx.channel().writeAndFlush(byteBuf); // 将消息写入通道并刷新
    }

    /**
     * 当客户端接收到服务端发送的数据时触发此方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg; // 将接收到的数据转换为 ByteBuf 类型
        log.info("接收到来自服务端的数据：{}", byteBuf.toString(StandardCharsets.UTF_8)); // 打印接收的数据（字符串）
    }
}
