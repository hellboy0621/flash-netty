package demo.im.client.console;

import demo.im.protocol.request.QuitGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class QuitGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("enter groupId to quit group chat: ");
        String groupId = scanner.next();
        QuitGroupRequestPacket quitGroupRequestPacket = QuitGroupRequestPacket.builder()
                .groupId(groupId)
                .build();
        channel.writeAndFlush(quitGroupRequestPacket);
    }
}
