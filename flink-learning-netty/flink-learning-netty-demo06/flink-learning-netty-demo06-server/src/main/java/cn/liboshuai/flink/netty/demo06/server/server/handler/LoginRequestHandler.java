package cn.liboshuai.flink.netty.demo06.server.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.LoginResponsePacket;
import cn.liboshuai.flink.netty.demo06.server.session.Session;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    /**
     * 随机生成 userId
     */
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) {
        log.info("接收到的客户端登录请求：{}", loginRequestPacket);

        // 构建登录响应包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion()); // 设置版本号
        loginResponsePacket.setUsername(loginRequestPacket.getUsername()); // 设置用户名称

        // 模拟校验登录逻辑
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            Session session = new Session(userId, loginRequestPacket.getUsername());
            // 绑定用户session信息与channel
            SessionUtils.bindSession(session, channelHandlerContext.channel());
        } else {
            loginResponsePacket.setSuccess(false); // 登录失败，并设置失败原因
            loginResponsePacket.setReason("账号密码验证失败！");
        }
        // 将登录响应数据返回给客户端
        log.info("返回给客户端登录响应：{}", loginResponsePacket);
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    // 模拟登录校验逻辑，暂时总是返回 true
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
