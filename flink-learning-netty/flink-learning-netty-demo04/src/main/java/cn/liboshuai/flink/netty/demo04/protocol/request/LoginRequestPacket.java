package cn.liboshuai.flink.netty.demo04.protocol.request;

import cn.liboshuai.flink.netty.demo04.protocol.Packet;
import cn.liboshuai.flink.netty.demo04.protocol.command.Command;
import lombok.*;

/**
 * 登录请求包，继承自 Packet，包含用户登录数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginRequestPacket extends Packet {

    private String userId; // 用户 ID
    private String username; // 用户名
    private String password; // 密码

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST; // 返回登录请求指令
    }
}
