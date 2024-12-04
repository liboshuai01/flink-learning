package cn.liboshuai.flink.netty.demo06.client.client.handler;

import cn.liboshuai.flink.netty.demo06.client.util.LoginUtil;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) {
        log.info("接收到的服务端登录响应：{}", loginResponsePacket); // 打印接收到的响应内容
        if (loginResponsePacket.isSuccess()) {
            log.info("登录成功！"); // 登录成功日志
            LoginUtil.markLogin(channelHandlerContext.channel()); // 标记登录成功
        } else {
            log.info("登录失败！原因：{}", loginResponsePacket.getReason()); // 登录失败日志
        }
    }
}
