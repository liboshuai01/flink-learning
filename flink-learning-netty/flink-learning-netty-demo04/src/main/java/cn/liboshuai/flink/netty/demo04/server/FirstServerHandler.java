package cn.liboshuai.flink.netty.demo04.server;

import cn.liboshuai.flink.netty.demo04.util.NettyUtils;
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

    /**
     * 当读取到客户端数据时触发此方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg; // 将接收到的数据转换为 Netty 的 ByteBuf 类型
        log.info("接收到来自客户端的数据：{}", byteBuf.toString(StandardCharsets.UTF_8)); // 打印接收的数据（字符串）

        // 服务端向客户端发送响应消息
        String message = "你好，我是服务端";
        ByteBuf out = NettyUtils.convertString2ByteBuf(message, ctx); // 将字符串消息转换为 ByteBuf
        ctx.channel().writeAndFlush(out); // 将响应消息写入通道并刷新
    }
}
