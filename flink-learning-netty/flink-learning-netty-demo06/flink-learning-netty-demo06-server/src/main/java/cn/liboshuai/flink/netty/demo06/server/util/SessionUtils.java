package cn.liboshuai.flink.netty.demo06.server.util;

import cn.liboshuai.flink.netty.demo06.common.session.Session;
import cn.liboshuai.flink.netty.demo06.server.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionUtils {

    private static final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupNameChannelGroupMap = new ConcurrentHashMap<>();

    /**
     * 绑定用户session信息与channel
     */
    public static void bindSession(Session session, Channel channel) {
        usernameChannelMap.put(session.getUsername(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解绑用户session信息与channel
     */
    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            usernameChannelMap.remove(getSession(channel).getUsername());
            channel.attr(Attributes.SESSION).set(null);
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
    public static Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    /**
     * 绑定群名称与ChannelGroup
     */
    public static void bindChannelGroup(String groupName, ChannelGroup channelGroup) {
        groupNameChannelGroupMap.put(groupName, channelGroup);
    }

    /**
     * 解绑群名称与ChannelGroup
     */
    public static void unbindChannelGroup(String groupName) {
        groupNameChannelGroupMap.remove(groupName);
    }

    /**
     * 通过群名称获取ChannelGroup
     */
    public static ChannelGroup getChannelGroup(String groupName) {
        return groupNameChannelGroupMap.get(groupName);
    }
}
