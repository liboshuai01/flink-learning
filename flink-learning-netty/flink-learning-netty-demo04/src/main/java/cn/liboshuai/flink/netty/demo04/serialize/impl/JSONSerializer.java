package cn.liboshuai.flink.netty.demo04.serialize.impl;

import cn.liboshuai.flink.netty.demo04.serialize.Serializer;
import cn.liboshuai.flink.netty.demo04.serialize.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
