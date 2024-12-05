package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.LogoutResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                LogoutResponsePacket logoutResponsePacket) throws Exception {
        log.debug("【退出登录】响应 logoutResponsePacket: {}", logoutResponsePacket);
        String consoleMessage;
        if (logoutResponsePacket.isSuccess()) {
            consoleMessage = "您已退出登录成功！";
        } else {
            consoleMessage = String.format("退出登录失败，原因为：[%s]", logoutResponsePacket.getReason());
        }
        System.out.println("\n" + consoleMessage);
    }
}
