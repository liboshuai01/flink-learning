package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.request.MessageRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.MessageResponsePacket;
import cn.liboshuai.flink.netty.demo06.common.session.Session;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    public static final SimpleChannelInboundHandler<? extends Packet> INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {
        log.debug("【私发消息】请求 messageRequestPacket：{}", messageRequestPacket);
        // 获取消息发送方的 session 信息
        Session session = SessionUtils.getSession(channelHandlerContext.channel());
        // 获取消息接收方的 channel 连接
        Channel toUserChannel = SessionUtils.getChannel(messageRequestPacket.getToUsername());
        // 响应客户端请求
        if (Objects.nonNull(toUserChannel) && SessionUtils.hasLogin(toUserChannel)) {
            // 构建消息响应包
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .fromUsername(session.getUsername())
                    .message(messageRequestPacket.getMessage())
                    .success(true)
                    .build();
            log.debug("【私发消息】响应 messageResponsePacket：{}", messageResponsePacket);
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            String failMessage = String.format("[%s]用户离线状态，发送失败！", messageRequestPacket.getToUsername());
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .success(false)
                    .reason(failMessage)
                    .build();
            log.debug("【私发消息】响应 messageResponsePacket：{}", messageResponsePacket);
            channelHandlerContext.writeAndFlush(messageResponsePacket);
        }
    }
}
