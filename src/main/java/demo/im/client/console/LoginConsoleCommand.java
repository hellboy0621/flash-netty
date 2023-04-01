package demo.im.client.console;

import demo.im.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        log.info("please input your user name to login:");
        String userName = scanner.nextLine();
        final LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .username(userName)
                .password("pwd")
                .build();

        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
