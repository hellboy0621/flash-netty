package demo.im.client.console;

import demo.im.protocol.request.ListGroupMembersRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ListGroupMembersConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("enter groupId to list group members :");
        String groupId = scanner.next();

        ListGroupMembersRequestPacket listGroupMembersRequestPacket = ListGroupMembersRequestPacket.builder()
                .groupId(groupId)
                .build();
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
