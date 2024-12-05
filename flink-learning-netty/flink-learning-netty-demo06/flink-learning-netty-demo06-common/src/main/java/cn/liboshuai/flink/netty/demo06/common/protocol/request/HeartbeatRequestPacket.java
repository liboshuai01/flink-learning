package cn.liboshuai.flink.netty.demo06.common.protocol.request;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HeartbeatRequestPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_REQUEST;
    }
}
