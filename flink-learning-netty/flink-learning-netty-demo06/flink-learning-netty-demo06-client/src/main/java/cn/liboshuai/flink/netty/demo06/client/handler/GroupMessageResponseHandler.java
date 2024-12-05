package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    public static final GroupMessageResponseHandler INSTANCE = new GroupMessageResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        log.debug("【群组消息】响应 groupMessageResponsePacket：{}", groupMessageResponsePacket);
        String consoleMessage = String.format("收到来自群组[%s]用户[%s]的消息，内容为：[%s]",
                groupMessageResponsePacket.getFromGroupName(), groupMessageResponsePacket.getFromUser().getUsername(),
                groupMessageResponsePacket.getMessage());
        System.out.println("\n" + consoleMessage);
    }
}
