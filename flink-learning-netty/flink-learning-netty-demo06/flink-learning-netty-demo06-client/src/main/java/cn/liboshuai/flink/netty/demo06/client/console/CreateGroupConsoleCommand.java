package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_DELIMITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============== 创建群聊 ==============");
        System.out.print("请输入群组名称：");
        String groupName = scanner.nextLine();
        System.out.print("请输入用户名列表（用英文逗号分隔，如 user1,user2,user3）：");
        String usernameList = scanner.nextLine();
        System.out.println("======================================");
        CreateGroupRequestPacket createGroupRequestPacket = CreateGroupRequestPacket.builder()
                .groupName(groupName)
                .usernameList(Arrays.asList(usernameList.split(USER_ID_DELIMITER)))
                .build();
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
