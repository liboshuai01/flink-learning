package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ChannelHandler.Sharable
public class IMResponseHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMResponseHandler INSTANCE = new IMResponseHandler();

    private final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlers = new HashMap<>();

    public IMResponseHandler() {
        handlers.put(Command.CREATE_GROUP_RESPONSE, new CreateGroupResponseHandler());
        handlers.put(Command.GROUP_MESSAGE_RESPONSE, new GroupMessageResponseHandler());
        handlers.put(Command.JOIN_GROUP_RESPONSE, new JoinGroupResponseHandler());
        handlers.put(Command.LIST_GROUP_MEMBERS_RESPONSE, new ListGroupMembersResponseHandler());
        handlers.put(Command.LOGIN_RESPONSE, new LoginResponseHandler());
        handlers.put(Command.LOGOUT_RESPONSE, new LogoutResponseHandler());
        handlers.put(Command.MESSAGE_RESPONSE, new MessageResponseHandler());
        handlers.put(Command.QUIT_GROUP_RESPONSE, new QuitGroupResponseHandler());
        handlers.put(Command.HEARTBEAT_RESPONSE, new HeartbeatResponseHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        handlers.get(packet.getCommand()).channelRead(channelHandlerContext, packet);
    }
}
