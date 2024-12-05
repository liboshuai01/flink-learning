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
public class GroupMessageRequestPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送群组名称
     */
    private String toGroupName;

    /**
     * 群发消息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
