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
public class QuitGroupResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 离开的群组名称
     */
    private String groupName;
    /**
     * 离开群组结果
     */
    private boolean success;
    /**
     * 离开群组失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
