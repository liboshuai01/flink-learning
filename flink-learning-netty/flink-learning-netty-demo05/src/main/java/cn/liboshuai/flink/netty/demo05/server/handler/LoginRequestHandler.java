package cn.liboshuai.flink.netty.demo05.server.handler;

import cn.liboshuai.flink.netty.demo05.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo05.protocol.response.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        log.info("接收到的客户端登录请求：{}", loginRequestPacket);

        // 构建登录响应包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion()); // 设置版本号

        // 模拟校验登录逻辑
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true); // 登录成功
        } else {
            loginResponsePacket.setSuccess(false); // 登录失败，并设置失败原因
            loginResponsePacket.setReason("账号密码验证失败！");
        }

        log.info("响应给客户端的登录响应：{}", loginResponsePacket);
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    // 模拟登录校验逻辑，暂时总是返回 true
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
