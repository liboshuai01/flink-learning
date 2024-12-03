package cn.liboshuai.flink.netty.demo05.server.handler;

import cn.liboshuai.flink.netty.demo05.protocol.request.MessageRequestPacket;
import cn.liboshuai.flink.netty.demo05.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {
        log.info("接收到的客户端消息数据：{}", messageRequestPacket);
        String responseMessage = String.format("服务端 [%s]", messageRequestPacket.getMessage());
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                .message(responseMessage)
                .build();
        log.info("响应给客户端消息数据：{}", messageResponsePacket);
        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
    }
}
