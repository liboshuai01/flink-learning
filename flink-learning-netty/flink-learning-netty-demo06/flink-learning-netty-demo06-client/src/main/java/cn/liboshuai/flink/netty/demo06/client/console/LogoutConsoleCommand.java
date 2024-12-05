package cn.liboshuai.flink.netty.demo06.client.console;

import cn.liboshuai.flink.netty.demo06.common.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        channel.writeAndFlush(new LogoutRequestPacket());
    }
}
