package demo.im.client.console;

import cn.hutool.core.util.StrUtil;
import demo.im.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = StrUtil.COMMA;

    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("[create group] enter userId list, separate by comma:");
        String userIdsStr = scanner.next();
        List<String> userIds = Arrays.asList(userIdsStr.split(USER_ID_SPLITER));
        CreateGroupRequestPacket createGroupRequestPacket = CreateGroupRequestPacket.builder()
                .userIds(userIds)
                .build();
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
