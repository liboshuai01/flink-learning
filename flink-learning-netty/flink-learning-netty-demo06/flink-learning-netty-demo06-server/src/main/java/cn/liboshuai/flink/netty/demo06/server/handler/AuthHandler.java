package cn.liboshuai.flink.netty.demo06.server.handler;

import cn.liboshuai.flink.netty.demo06.server.util.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (SessionUtils.hasLogin(ctx.channel())) {
            log.info("AuthHandler 登录效验通过！");
            ctx.pipeline().remove(this); // 一次登录成功效验后，即可移除
            super.channelRead(ctx, msg);
        } else {
            log.info("AuthHandler 登录效验失败！");
            ctx.channel().close(); // 登录效验不通过，则直接关闭连接
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("AuthHandler 被移除！");
    }
}
