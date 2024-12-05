package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============== 群组列表 ==============");
        System.out.print("请输入群组名称：");
        String groupName = scanner.nextLine();
        System.out.println("======================================");
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = ListGroupMembersRequestPacket.builder()
                .groupName(groupName)
                .build();
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
