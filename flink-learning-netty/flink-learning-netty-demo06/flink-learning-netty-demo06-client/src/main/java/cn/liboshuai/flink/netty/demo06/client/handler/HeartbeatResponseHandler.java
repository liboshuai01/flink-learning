package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.response.HeartbeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class HeartbeatResponseHandler extends SimpleChannelInboundHandler<HeartbeatResponsePacket> {

    public static final HeartbeatResponseHandler INSTANCE = new HeartbeatResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                HeartbeatResponsePacket heartbeatResponsePacket) throws Exception {
    }
}
