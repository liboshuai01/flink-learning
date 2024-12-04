package cn.liboshuai.flink.netty.demo06.server.util;

import cn.liboshuai.flink.netty.demo06.server.attribute.Attributes;
import cn.liboshuai.flink.netty.demo06.server.session.Session;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionUtils {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    /**
     * 绑定用户session信息与channel
     */
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解绑用户session信息与channel
     */
    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(channel.attr(Attributes.SESSION).get().getUserId());
        }
    }

    /**
     * 判断当前 channel 是否登录
     */
    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 通过 channel 获取 session 信息
     */
    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 通过 session 信息 获取 channel 连接
     */
    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }
}
