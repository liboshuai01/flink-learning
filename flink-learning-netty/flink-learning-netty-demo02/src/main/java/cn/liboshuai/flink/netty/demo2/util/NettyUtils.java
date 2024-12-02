package cn.liboshuai.flink.netty.demo2.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public class NettyUtils {
    private ByteBuf convertString2ByteBuf(String str, ChannelHandlerContext ctx) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
