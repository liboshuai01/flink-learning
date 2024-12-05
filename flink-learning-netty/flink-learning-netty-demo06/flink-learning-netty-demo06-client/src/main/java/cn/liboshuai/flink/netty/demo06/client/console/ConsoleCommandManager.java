package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.client.util.LoginUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class ConsoleCommandManager implements ConsoleCommand {

    private final Map<String, ConsoleCommand> consoleCommandMap = new HashMap<>();

    public ConsoleCommandManager() {
        consoleCommandMap.put("sendToUser", new SendToUserConsoleCommand());
        consoleCommandMap.put("logout", new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup", new CreateGroupConsoleCommand());
        consoleCommandMap.put("joinGroup", new JoinGroupConsoleCommand());
        consoleCommandMap.put("quitGroup", new QuitGroupConsoleCommand());
        consoleCommandMap.put("listGroupMembers", new ListGroupMembersConsoleCommand());
        consoleCommandMap.put("sendToGroup", new SendToGroupConsoleCommand());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============= 功能列表 =============");
        System.out.printf("%-20s %s\n", "logout", "退出登录");
        System.out.printf("%-20s %s\n", "sendToUser", "私发消息");
        System.out.printf("%-20s %s\n", "createGroup", "创建群组");
        System.out.printf("%-20s %s\n", "joinGroup", "加入群组");
        System.out.printf("%-20s %s\n", "listGroupMembers", "群组列表");
        System.out.printf("%-20s %s\n", "sendToGroup", "群发消息");
        System.out.printf("%-20s %s\n", "quitGroup", "退出群组");
        System.out.println("===================================");
        System.out.print("请输入指令: ");
        String command = scanner.nextLine();
        if (!LoginUtil.hasLogin(channel)) {
            System.out.println("请先进行登录！");
            return;
        }
        ConsoleCommand consoleCommand = consoleCommandMap.get(command);
        if (Objects.nonNull(consoleCommand)) {
            consoleCommand.exec(scanner, channel);
        } else {
            String consoleMessage = String.format("无法识别[%s]指令，请重新输入！", command);
            System.out.println(consoleMessage);
        }
    }
}
