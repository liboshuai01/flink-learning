package cn.liboshuai.flink.netty.demo04.serialize;

import cn.liboshuai.flink.netty.demo04.serialize.impl.JSONSerializer;

/**
 * 定义一个序列化接口，提供序列化和反序列化的能力
 */
public interface Serializer {
    // 默认序列化器为 JSONSerializer
    Serializer DEFAULT = new JSONSerializer();

    // 获取序列化算法的标识
    byte getSerializerAlgorithm();

    // 将对象序列化为字节数组
    byte[] serialize(Object obj);

    // 将字节数组反序列化为对象
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
