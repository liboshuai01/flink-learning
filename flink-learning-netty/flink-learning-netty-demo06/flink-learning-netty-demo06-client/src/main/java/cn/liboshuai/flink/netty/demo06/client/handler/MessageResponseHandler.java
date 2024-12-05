package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    public static final MessageResponseHandler instance = new MessageResponseHandler();

    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) {
        log.debug("【私发消息】响应 messageResponsePacket：{}", messageResponsePacket);
        String consoleMessage;
        if (messageResponsePacket.isSuccess()) {
            consoleMessage = String.format("接收到来自用户[%s]的私发消息，内容为：[%s]",
                    messageResponsePacket.getFromUsername(), messageResponsePacket.getMessage());
        } else {
            consoleMessage = messageResponsePacket.getReason();
        }
        System.out.println("\n" + consoleMessage);
    }
}
