package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        log.debug("【创建群组】响应 createGroupResponsePacket：{}", createGroupResponsePacket);
        String consoleMessage;
        if (createGroupResponsePacket.isSuccess()) {
            consoleMessage = String.format("您已成功加入到[%s]群组中",
                    createGroupResponsePacket.getGroupName());
        } else {
            consoleMessage = String.format("您尝试加入到[%s]群组中失败，原因为：[%s]",
                    createGroupResponsePacket.getGroupName(), createGroupResponsePacket.getReason());
        }
        System.out.println("\n" + consoleMessage);
    }
}
