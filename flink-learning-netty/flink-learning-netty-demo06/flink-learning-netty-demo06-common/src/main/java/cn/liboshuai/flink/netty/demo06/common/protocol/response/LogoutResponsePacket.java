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
public class LogoutResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 注销结果
     */
    private boolean success;
    /**
     * 注销失败原因
     */
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
