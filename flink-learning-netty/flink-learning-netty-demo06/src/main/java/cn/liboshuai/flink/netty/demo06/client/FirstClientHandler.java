package cn.liboshuai.flink.netty.demo06.client;

import cn.liboshuai.flink.netty.demo06.util.NettyUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端的自定义处理类，扩展自 Netty 的 ChannelInboundHandlerAdapter 类
 */
@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当客户端连接建立成功后触发此方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf = NettyUtils.INSTANCE.convertString2ByteBuf("这里是客户端，收到请回复！", ctx);
        ctx.channel().writeAndFlush(byteBuf);
    }

}
