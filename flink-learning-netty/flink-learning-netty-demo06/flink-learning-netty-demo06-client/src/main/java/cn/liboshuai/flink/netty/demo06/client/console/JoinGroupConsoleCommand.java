package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============== 加入群组 ==============");
        System.out.print("请输入群组名称：");
        String groupName = scanner.nextLine();
        System.out.println("======================================");
        JoinGroupRequestPacket listGroupMembersRequestPacket = JoinGroupRequestPacket.builder()
                .groupName(groupName)
                .build();
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
