package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.LogoutRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.LogoutResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {

    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                LogoutRequestPacket logoutRequestPacket) throws Exception {
        log.debug("【退出登录】请求 logoutRequestPacket：{}", logoutRequestPacket);
        SessionUtils.unbindSession(channelHandlerContext.channel());
        LogoutResponsePacket logoutResponsePacket = LogoutResponsePacket.builder()
                .success(true)
                .build();
        log.debug("【退出登录】响应 logoutResponsePacket：{}", logoutResponsePacket);
        channelHandlerContext.writeAndFlush(logoutResponsePacket);
    }
}
