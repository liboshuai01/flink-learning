package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.ListGroupMembersResponsePacket;
import cn.liboshuai.flink.netty.demo06.common.session.Session;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ChannelHandler.Sharable
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    public static final ListGroupMembersResponseHandler INSTANCE = new ListGroupMembersResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        log.debug("【群组列表】响应 listGroupMembersResponsePacket: {}", listGroupMembersResponsePacket);
        List<String> usernameList = listGroupMembersResponsePacket.getSessionList()
                .stream().map(Session::getUsername).collect(Collectors.toList());
        String consoleMessage = String.format("群组[%s]包含以下用户：[%s]",
                listGroupMembersResponsePacket.getGroupName(), String.join(",", usernameList));
        System.out.println("\n" + consoleMessage);
    }
}
