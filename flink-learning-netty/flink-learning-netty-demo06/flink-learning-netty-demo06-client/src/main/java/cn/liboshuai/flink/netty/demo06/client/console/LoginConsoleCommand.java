package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LoginConsoleCommand implements ConsoleCommand {
    private static void waitForLoginResponse() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("============== 用户登录 ==============");
        System.out.print("请输入用户名称：");
        String username = scanner.nextLine();
        System.out.println("======================================");
        channel.writeAndFlush(new LoginRequestPacket(username, "123456"));
        waitForLoginResponse();
    }
}
