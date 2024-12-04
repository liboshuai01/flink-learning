package cn.liboshuai.flink.netty.demo06.server.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.MessageRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.MessageResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.session.Session;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) {
        log.info("接收到的客户端消息数据：{}", messageRequestPacket);
        // 获取消息发送方的 session 信息
        Session session = SessionUtils.getSession(channelHandlerContext.channel());
        // 获取消息接收方的 channel 连接
        Channel toUserChannel = SessionUtils.getChannel(messageRequestPacket.getToUserId());
        // 响应客户端请求
        if (Objects.nonNull(toUserChannel) && SessionUtils.hasLogin(toUserChannel)) {
            // 构建消息响应包
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .fromUserId(session.getUserId())
                    .fromUsername(session.getUsername())
                    .message(messageRequestPacket.getMessage())
                    .success(true)
                    .build();
            log.info("转发响应客户端信息成功：{}", messageRequestPacket);
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            String failMessage = String.format("[%s]用户离线状态，发送失败！", messageRequestPacket.getToUserId());
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .success(false)
                    .reason(failMessage)
                    .build();
            log.info("转发响应客户端信息失败：{}", messageRequestPacket);
            channelHandlerContext.channel().writeAndFlush(messageResponsePacket);
        }
    }
}
