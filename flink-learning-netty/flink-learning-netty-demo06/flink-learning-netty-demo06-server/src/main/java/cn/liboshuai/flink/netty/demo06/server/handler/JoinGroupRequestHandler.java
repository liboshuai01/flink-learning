package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.JoinGroupRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.JoinGroupResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        log.debug("【加入群组】请求 joinGroupRequestPacket：{}", joinGroupRequestPacket);
        String groupName = joinGroupRequestPacket.getGroupName();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupName);
        channelGroup.add(channelHandlerContext.channel());
        JoinGroupResponsePacket joinGroupResponsePacket = JoinGroupResponsePacket.builder()
                .groupName(groupName)
                .success(true)
                .build();
        log.debug("【加入群组】响应 joinGroupResponsePacket：{}", joinGroupResponsePacket);
        channelHandlerContext.writeAndFlush(joinGroupResponsePacket);
    }
}
