package cn.liboshuai.flink.netty.demo2.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

/**
 * 工具类，包含与 Netty 字符串与 ByteBuf 的转换方法
 */
public class NettyUtils {

    // 将字符串转换为 Netty 的 ByteBuf
    public static ByteBuf convertString2ByteBuf(String str, ChannelHandlerContext ctx) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8); // 将字符串编码为 UTF-8 字节数组
        ByteBuf byteBuf = ctx.alloc().buffer(); // 分配一个 ByteBuf
        byteBuf.writeBytes(bytes); // 将字节数组写入 ByteBuf
        return byteBuf; // 返回封装后的 ByteBuf
    }
}
