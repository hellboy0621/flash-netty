package demo.im.client.console;

import demo.im.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("send message to group: ");
        String groupId = scanner.next();
        String message = scanner.next();

        GroupMessageRequestPacket groupMessageRequestPacket = GroupMessageRequestPacket.builder()
                .groupId(groupId)
                .message(message)
                .build();
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
