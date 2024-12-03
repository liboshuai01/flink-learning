package cn.liboshuai.flink.netty.demo04.serialize;

import cn.liboshuai.flink.netty.demo04.serialize.impl.JSONSerializer;

public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();


    byte getSerializerAlgorithm();

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
