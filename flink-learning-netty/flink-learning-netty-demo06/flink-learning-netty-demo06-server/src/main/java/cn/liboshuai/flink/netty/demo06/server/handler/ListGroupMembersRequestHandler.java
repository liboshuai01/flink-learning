package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.ListGroupMembersRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.ListGroupMembersResponsePacket;
import cn.liboshuai.flink.netty.demo06.common.session.Session;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        log.debug("【群组列表】请求 listGroupMembersRequestPacket：{}", listGroupMembersRequestPacket);
        String groupName = listGroupMembersRequestPacket.getGroupName();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupName);
        // 获取channel群组对应用户session信息
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtils.getSession(channel);
            sessionList.add(session);
        }
        // 响应给客户端
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = new ListGroupMembersResponsePacket(
                groupName, sessionList
        );
        log.debug("【群组列表】响应 listGroupMembersResponsePacket：{}", listGroupMembersResponsePacket);
        channelHandlerContext.writeAndFlush(listGroupMembersResponsePacket);
    }
}
