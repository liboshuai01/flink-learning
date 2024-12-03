package cn.liboshuai.flink.netty.demo05.client.handler;

import cn.liboshuai.flink.netty.demo05.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo05.protocol.response.LoginResponsePacket;
import cn.liboshuai.flink.netty.demo05.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 构建一个登录请求数据包
        LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .userId(UUID.randomUUID().toString()) // 随机生成用户 ID
                .username("liboshuai") // 演示用户名
                .password("123456") // 演示密码
                .build();

        log.info("向服务器发送登录请求：{}", loginRequestPacket); // 打印日志
        ctx.channel().writeAndFlush(loginRequestPacket); // 发送请求到服务端
    }

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
