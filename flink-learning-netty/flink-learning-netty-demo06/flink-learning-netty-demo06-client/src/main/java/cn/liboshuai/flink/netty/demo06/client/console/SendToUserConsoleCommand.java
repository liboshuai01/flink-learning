package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============== 私发消息 ==============");
        System.out.print("请输入用户名称：");
        String username = scanner.nextLine();
        System.out.print("请输入私发内容：");
        String message = scanner.nextLine();
        System.out.println("======================================");
        MessageRequestPacket messageRequestPacket = MessageRequestPacket.builder()
                .toUsername(username)
                .message(message)
                .build();
        channel.writeAndFlush(messageRequestPacket);
    }
}
