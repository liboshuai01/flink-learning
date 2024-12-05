package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.HeartbeatRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.HeartbeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class HeartbeatRequestHandler extends SimpleChannelInboundHandler<HeartbeatRequestPacket> {

    public static final HeartbeatRequestHandler INSTANCE = new HeartbeatRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                HeartbeatRequestPacket heartbeatRequestPacket) throws Exception {
        channelHandlerContext.writeAndFlush(new HeartbeatResponsePacket());
    }
}
