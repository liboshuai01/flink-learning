package cn.liboshuai.flink.netty.demo06.common.protocol.response;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送方 username
     */
    private String fromUsername;
    /**
     * 发送信息内容
     */
    private String message;
    /**
     * 发送状态
     */
    private boolean success;
    /**
     * 失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
