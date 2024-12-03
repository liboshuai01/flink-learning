package cn.liboshuai.flink.netty.demo05.protocol.request;

import cn.liboshuai.flink.netty.demo05.protocol.Packet;
import cn.liboshuai.flink.netty.demo05.protocol.command.Command;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageRequestPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
