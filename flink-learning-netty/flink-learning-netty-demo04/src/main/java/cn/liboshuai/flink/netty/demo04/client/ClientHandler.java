package cn.liboshuai.flink.netty.demo04.client;

import cn.liboshuai.flink.netty.demo04.protocol.Packet;
import cn.liboshuai.flink.netty.demo04.protocol.PacketCodeC;
import cn.liboshuai.flink.netty.demo04.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo04.protocol.response.LoginResponsePacket;
import cn.liboshuai.flink.netty.demo04.protocol.response.MessageResponsePacket;
import cn.liboshuai.flink.netty.demo04.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    // 当连接建立时触发（channelActive）
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 构建一个登录请求数据包
        LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .userId(UUID.randomUUID().toString()) // 随机生成用户 ID
                .username("liboshuai") // 演示用户名
                .password("123456") // 演示密码
                .build();

        log.info("向服务器发送登录请求：{}", loginRequestPacket); // 打印日志
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket); // 编码登录请求包
        ctx.channel().writeAndFlush(byteBuf); // 发送请求到服务端
    }

    // 当客户端接收到数据时触发（channelRead）
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg; // 接收到来自服务端的 ByteBuf 数据
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf); // 解码为通用数据包

        // 判断是否为登录响应包
        if (packet instanceof LoginResponsePacket) {
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
            log.info("接收到的服务端登录响应：{}", loginResponsePacket); // 打印接收到的响应内容
            if (loginResponsePacket.isSuccess()) {
                log.info("登录成功！"); // 登录成功日志
                LoginUtil.markLogin(ctx.channel()); // 标记登录成功
            } else {
                log.info("登录失败！原因：{}", loginResponsePacket.getReason()); // 登录失败日志
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            log.info("接收到的服务端消息数据：{}", messageResponsePacket);
        } else {
            log.info("其他类型的数据请求暂不支持！"); // 如果不是登录响应包，则打印不支持的信息
        }
    }
}
