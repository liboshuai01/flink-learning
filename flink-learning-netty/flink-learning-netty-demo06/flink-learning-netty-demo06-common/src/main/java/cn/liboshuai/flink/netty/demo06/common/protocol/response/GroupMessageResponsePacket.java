package cn.liboshuai.flink.netty.demo06.common.protocol.response;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import cn.liboshuai.flink.netty.demo06.common.session.Session;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GroupMessageResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息来源的群组名称
     */
    private String fromGroupName;
    /**
     * 消息发送者的 session 信息
     */
    private Session fromUser;
    /**
     * 群发消息内容
     */
    private String message;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
