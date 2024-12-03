package cn.liboshuai.flink.netty.demo04.server;

import cn.liboshuai.flink.netty.demo04.protocol.Packet;
import cn.liboshuai.flink.netty.demo04.protocol.PacketCodeC;
import cn.liboshuai.flink.netty.demo04.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo04.protocol.request.MessageRequestPacket;
import cn.liboshuai.flink.netty.demo04.protocol.response.LoginResponsePacket;
import cn.liboshuai.flink.netty.demo04.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    // 处理收到的客户端发来的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg; // 接收客户端发来的 ByteBuf
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf); // 解码成 Packet 数据包

        // 判断是否为登录请求包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            log.info("接收到的客户端登录请求：{}", loginRequestPacket);

            // 构建登录响应包
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion()); // 设置版本号

            // 模拟校验登录逻辑
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true); // 登录成功
            } else {
                loginResponsePacket.setSuccess(false); // 登录失败，并设置失败原因
                loginResponsePacket.setReason("账号密码验证失败！");
            }

            log.info("响应给客户端的登录响应：{}", loginResponsePacket);

            // 编码响应包并发送给客户端
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            log.info("接收到的客户端消息数据：{}", messageRequestPacket);
            String responseMessage = String.format("服务端 [%s]", messageRequestPacket.getMessage());
            log.info("响应给客户端消息数据：{}", responseMessage);
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .message(responseMessage)
                    .build();
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else {
            // 暂不支持其他类型的数据请求
            log.info("其他类型的数据请求暂不支持！");
        }
    }

    // 模拟登录校验逻辑，暂时总是返回 true
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
