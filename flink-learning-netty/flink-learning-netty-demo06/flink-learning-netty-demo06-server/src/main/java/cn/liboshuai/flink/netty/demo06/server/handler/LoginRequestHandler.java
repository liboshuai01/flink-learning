package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo06.common.protocol.response.LoginResponsePacket;
import cn.liboshuai.flink.netty.demo06.common.session.Session;
import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) {
        log.debug("登录请求 loginRequestPacket：{}", loginRequestPacket);
        // 构建登录响应包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setUsername(loginRequestPacket.getUsername()); // 设置用户名称

        // 模拟校验登录逻辑
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            Session session = new Session(loginRequestPacket.getUsername());
            // 绑定用户session信息与channel
            SessionUtils.bindSession(session, channelHandlerContext.channel());
        } else {
            loginResponsePacket.setSuccess(false); // 登录失败，并设置失败原因
            loginResponsePacket.setReason("账号密码验证失败！");
        }
        // 将登录响应数据返回给客户端
        log.debug("登录响应 loginResponsePacket：{}", loginResponsePacket);
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    // 模拟登录校验逻辑
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        String password = loginRequestPacket.getPassword();
        return Objects.equals(password, "123456");
    }
}
