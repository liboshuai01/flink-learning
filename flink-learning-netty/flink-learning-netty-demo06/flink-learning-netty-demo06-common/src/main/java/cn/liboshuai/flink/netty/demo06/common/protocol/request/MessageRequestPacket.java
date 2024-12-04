package cn.liboshuai.flink.netty.demo06.common.protocol.request;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageRequestPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息接收方 userId
     */
    private String toUserId;

    /**
     * 发送信息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
