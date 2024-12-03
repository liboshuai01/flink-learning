package cn.liboshuai.flink.netty.demo05.client.handler;

import cn.liboshuai.flink.netty.demo05.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) {
        log.info("接收到的服务端消息数据：{}", messageResponsePacket);
    }
}
