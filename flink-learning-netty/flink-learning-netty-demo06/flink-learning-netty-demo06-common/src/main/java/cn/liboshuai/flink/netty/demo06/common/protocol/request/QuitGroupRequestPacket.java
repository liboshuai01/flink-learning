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
public class QuitGroupRequestPacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 需要离开的群组名称
     */
    private String groupName;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_REQUEST;
    }
}
