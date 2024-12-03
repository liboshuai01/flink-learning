package cn.liboshuai.flink.netty.demo04.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 数据包的基类，定义通用属性和方法
 */
@Data
public abstract class Packet {

    // 版本号，默认为 1，JSONField 注解表示不参与序列化和反序列化
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    // 获取指令标识，子类需实现该方法，JSONField 注解表示不参与序列化
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
