package cn.liboshuai.flink.netty.demo04.protocol.command;

import cn.liboshuai.flink.netty.demo04.serialize.Serializer;
import cn.liboshuai.flink.netty.demo04.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);

        serializerMap = new HashMap<>();
        JSONSerializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);
        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (Objects.nonNull(serializer) && Objects.nonNull(requestType)) {
            return serializer.deserialize(bytes, requestType);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
