package cn.liboshuai.flink.netty.demo06.common.protocol.response;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import lombok.*;

/**
 * 登录响应包，继承自 Packet，定义登录响应结果
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet {
    private String userId; // 用户ID
    private String username; // 用户名称
    private boolean success; // 登录是否成功
    private String reason; // 失败原因

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE; // 返回登录响应指令
    }
}

