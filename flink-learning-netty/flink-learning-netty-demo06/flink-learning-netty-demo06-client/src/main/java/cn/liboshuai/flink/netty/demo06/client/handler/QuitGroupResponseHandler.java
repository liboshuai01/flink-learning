package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        log.debug("【退出群组】响应 quitGroupResponsePacket：{}", quitGroupResponsePacket);
        String consoleMessage;
        if (quitGroupResponsePacket.isSuccess()) {
            consoleMessage = String.format("您已成功退出[%s]群组", quitGroupResponsePacket.getGroupName());
        } else {
            consoleMessage = String.format("您退出[%s]群组失败，原因为：[%s]",
                    quitGroupResponsePacket.getGroupName(), quitGroupResponsePacket.getReason());
        }
        System.out.println("\n" + consoleMessage);
    }
}
