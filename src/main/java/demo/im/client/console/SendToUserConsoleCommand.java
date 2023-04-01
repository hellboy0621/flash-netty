package demo.im.client.console;

import demo.im.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("send message to user :");
        String toUserId = scanner.next();
        String message = scanner.next();
        channel.writeAndFlush(MessageRequestPacket.builder()
                .toUserId(toUserId)
                .message(message)
                .build());
    }
}
