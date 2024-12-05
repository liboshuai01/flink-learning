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
public class CreateGroupResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建群组结果
     */
    private boolean success;

    /**
     * 创建群组失败的原因
     */
    private String reason;

    /**
     * 创建的群组名称
     */
    private String groupName;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
