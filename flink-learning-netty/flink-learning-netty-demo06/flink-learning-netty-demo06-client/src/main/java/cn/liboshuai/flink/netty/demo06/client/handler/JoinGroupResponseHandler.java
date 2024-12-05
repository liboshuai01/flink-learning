package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        log.debug("【加入群组】响应 joinGroupResponsePacket：{}", joinGroupResponsePacket);
        String consoleMessage;
        if (joinGroupResponsePacket.isSuccess()) {
            consoleMessage = String.format("您已加入群组[%s]中", joinGroupResponsePacket.getGroupName());
        } else {
            consoleMessage = String.format("您尝试加入群组[%s]中失败，原因为：[%s]",
                    joinGroupResponsePacket.getGroupName(), joinGroupResponsePacket.getReason());
        }
        System.out.println("\n" + consoleMessage);
    }
}
