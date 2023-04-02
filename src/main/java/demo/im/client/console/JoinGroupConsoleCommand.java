package demo.im.client.console;

import demo.im.protocol.request.JoinGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("enter groupId to join group chat: ");
        String groupId = scanner.next();
        JoinGroupRequestPacket joinGroupRequestPacket = JoinGroupRequestPacket.builder()
                .groupId(groupId)
                .build();
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
