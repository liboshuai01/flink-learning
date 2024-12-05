package cn.liboshuai.flink.netty.demo06.server.attribute;

import cn.liboshuai.flink.netty.demo06.common.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
