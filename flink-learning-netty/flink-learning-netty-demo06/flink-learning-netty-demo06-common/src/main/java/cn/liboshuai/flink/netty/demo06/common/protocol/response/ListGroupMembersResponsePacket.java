package cn.liboshuai.flink.netty.demo06.common.protocol.response;

import cn.liboshuai.flink.netty.demo06.common.protocol.Packet;
import cn.liboshuai.flink.netty.demo06.common.protocol.command.Command;
import cn.liboshuai.flink.netty.demo06.common.session.Session;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ListGroupMembersResponsePacket extends Packet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 需要展示用户列表的群组名称
     */
    private String groupName;

    /**
     * 群组中的用户列表 session 信息
     */
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
