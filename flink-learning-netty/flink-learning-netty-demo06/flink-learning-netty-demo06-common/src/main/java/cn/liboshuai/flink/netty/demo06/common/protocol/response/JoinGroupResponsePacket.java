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
public class JoinGroupResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 加入的群组名称
     */
    private String groupName;
    /**
     * 加入群组结果
     */
    private boolean success;
    /**
     * 加入群组失败的原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
