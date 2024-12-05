package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.CreateGroupRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.CreateGroupResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        log.debug("【创建群组】请求 createGroupRequestPacket：{}", createGroupRequestPacket);
        List<String> usernameList = createGroupRequestPacket.getUsernameList();
        // 将在线的用户channel加入到ChannelGroup中
        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());
        for (String username : usernameList) {
            Channel channel = SessionUtils.getChannel(username);
            if (channel != null) {
                channelGroup.add(channel);
            }
        }
        // 绑定群名称与ChannelGroup
        SessionUtils.bindChannelGroup(createGroupRequestPacket.getGroupName(), channelGroup);
        // 响应创建群
        CreateGroupResponsePacket createGroupResponsePacket = CreateGroupResponsePacket.builder()
                .success(true)
                .groupName(createGroupRequestPacket.getGroupName())
                .build();
        // 给群中的每一个客户端发送创建结果消息
        log.debug("【创建群组】响应 createGroupResponsePacket：{}", createGroupResponsePacket);
        channelGroup.writeAndFlush(createGroupResponsePacket);
    }
}
