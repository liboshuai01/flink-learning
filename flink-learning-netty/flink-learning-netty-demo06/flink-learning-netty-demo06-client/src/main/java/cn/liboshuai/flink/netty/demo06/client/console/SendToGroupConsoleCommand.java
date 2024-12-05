package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============== 群发消息 ==============");
        System.out.print("请输入群组名称：");
        String groupName = scanner.nextLine();
        System.out.print("请输入群发内容：");
        String message = scanner.nextLine();
        System.out.println("======================================");
        GroupMessageRequestPacket groupMessageRequestPacket = GroupMessageRequestPacket.builder()
                .toGroupName(groupName)
                .message(message)
                .build();
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
