package cn.liboshuai.flink.netty.demo06.client.util;

import cn.liboshuai.flink.netty.demo06.client.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
    public static void markLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);

        return loginAttr.get() != null;
    }
}
