package cn.liboshuai.flink.netty.demo06.client.handler;

import cn.liboshuai.flink.netty.demo06.client.util.LoginUtil;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public static final LoginResponseHandler INSTANCE = new LoginResponseHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) {
        log.debug("【进行登录】响应 loginResponsePacket：{}", loginResponsePacket);
        String consoleMessage;
        if (loginResponsePacket.isSuccess()) {
            LoginUtil.markLogin(channelHandlerContext.channel()); // 标记登录成功
            consoleMessage = String.format("用户[%s]您好，登录成功！", loginResponsePacket.getUsername());
        } else {
            consoleMessage = String.format("用户[%s]您好，登录失败！原因：%s",
                    loginResponsePacket.getUsername(), loginResponsePacket.getReason());
        }
        System.out.println("\n" + consoleMessage);
    }
}
