package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.QuitGroupRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.QuitGroupResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        log.debug("【退出群组】请求 quitGroupRequestPacket：{}", quitGroupRequestPacket);
        String groupName = quitGroupRequestPacket.getGroupName();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupName);
        channelGroup.remove(channelHandlerContext.channel());
        QuitGroupResponsePacket quitGroupResponsePacket = QuitGroupResponsePacket.builder()
                .groupName(groupName)
                .success(true)
                .build();
        log.debug("【退出群组】响应 quitGroupResponsePacket：{}", quitGroupResponsePacket);
        channelHandlerContext.writeAndFlush(quitGroupResponsePacket);
    }
}
