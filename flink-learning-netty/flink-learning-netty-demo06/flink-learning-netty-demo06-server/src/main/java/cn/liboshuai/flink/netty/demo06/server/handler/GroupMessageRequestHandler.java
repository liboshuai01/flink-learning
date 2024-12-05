package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.GroupMessageRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.GroupMessageResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        log.debug("【群发消息】请求 groupMessageRequestPacket：{}", groupMessageRequestPacket);
        GroupMessageResponsePacket groupMessageResponsePacket = GroupMessageResponsePacket.builder()
                .fromGroupName(groupMessageRequestPacket.getToGroupName())
                .fromUser(SessionUtils.getSession(channelHandlerContext.channel()))
                .message(groupMessageRequestPacket.getMessage())
                .build();
        log.debug("【群发消息】响应 groupMessageResponsePacket：{}", groupMessageResponsePacket);
        SessionUtils.getChannelGroup(groupMessageRequestPacket.getToGroupName()).writeAndFlush(groupMessageResponsePacket);
    }
}
