package cn.liboshuai.flink.netty.demo06.common.serialize;

/**
 * 定义序列化算法标识接口（目前支持 JSON 算法）
 */
public interface SerializerAlgorithm {
    // JSON 序列化算法标识
    byte JSON = 1;
}
