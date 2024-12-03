package cn.liboshuai.flink.netty.demo04.protocol;

import cn.liboshuai.flink.netty.demo04.protocol.command.Command;
import cn.liboshuai.flink.netty.demo04.protocol.request.LoginRequestPacket;
import cn.liboshuai.flink.netty.demo04.protocol.response.LoginResponsePacket;
import cn.liboshuai.flink.netty.demo04.serialize.Serializer;
import cn.liboshuai.flink.netty.demo04.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 编解码器，用于将数据包编码为字节流和从字节流中解码数据包
 */
public class PacketCodeC {

    // 单例模式，方便在整个工程中使用
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    // 魔数，用于识别是否是合法的数据包
    private static final int MAGIC_NUMBER = 0x12345678;

    // Command 对应的 Packet 类型映射表
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    // 序列化算法对应的实现类映射表
    private static final Map<Byte, Serializer> serializerMap;

    static {
        // 初始化 Packet 类型映射表
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        // 初始化序列化器映射表
        serializerMap = new HashMap<>();
        JSONSerializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    // 编码方法，将 Packet 转为 ByteBuf
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        ByteBuf byteBuf = byteBufAllocator.ioBuffer(); // 分配 ByteBuf 缓存

        byte[] bytes = Serializer.DEFAULT.serialize(packet); // 序列化数据包为字节数组

        // 按协议格式写入数据
        byteBuf.writeInt(MAGIC_NUMBER); // 魔数
        byteBuf.writeByte(packet.getVersion()); // 版本号
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm()); // 序列化算法标识
        byteBuf.writeByte(packet.getCommand()); // 指令标识
        byteBuf.writeInt(bytes.length); // 数据长度
        byteBuf.writeBytes(bytes); // 数据内容

        return byteBuf;
    }

    // 解码方法，从 ByteBuf 解码为 Packet
    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4); // 跳过魔数
        byteBuf.skipBytes(1); // 跳过版本号
        byte serializeAlgorithm = byteBuf.readByte(); // 读取序列化算法标识
        byte command = byteBuf.readByte(); // 读取指令标识
        int length = byteBuf.readInt(); // 读取数据长度
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes); // 读取实际数据内容

        // 根据指令标识获取请求类型
        Class<? extends Packet> requestType = getRequestType(command);

        // 根据序列化算法标识获取序列化器
        Serializer serializer = getSerializer(serializeAlgorithm);

        // 如果请求类型和序列化器均非空，则进行反序列化
        if (Objects.nonNull(serializer) && Objects.nonNull(requestType)) {
            return serializer.deserialize(bytes, requestType);
        }
        return null; // 非法数据包返回 null
    }

    // 根据序列化算法标识获取对应的序列化实现
    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    // 根据指令标识获取对应的 Packet 类型
    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
