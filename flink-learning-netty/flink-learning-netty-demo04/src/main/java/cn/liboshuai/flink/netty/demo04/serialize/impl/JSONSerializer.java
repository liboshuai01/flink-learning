package cn.liboshuai.flink.netty.demo04.serialize.impl;

import cn.liboshuai.flink.netty.demo04.serialize.Serializer;
import cn.liboshuai.flink.netty.demo04.serialize.SerializerAlgorithm;
import com.alibaba.fastjson.JSON;

/**
 * JSON 序列化实现类，提供 JSON 的序列化和反序列化功能
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON; // 返回 JSON 序列化算法标识
    }

    @Override
    public byte[] serialize(Object obj) {
        // 使用 JSON 工具将对象序列化为字节数组
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        // 使用 JSON 工具将字节数组反序列化为指定对象
        return JSON.parseObject(bytes, clazz);
    }
}
