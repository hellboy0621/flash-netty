package demo.im.client;

import demo.im.client.console.ConsoleCommandManager;
import demo.im.client.console.LoginConsoleCommand;
import demo.im.client.handler.CreateGroupResponseHandler;
import demo.im.client.handler.GroupMessageResponseHandler;
import demo.im.client.handler.JoinGroupResponseHandler;
import demo.im.client.handler.ListGroupMembersResponseHandler;
import demo.im.client.handler.LoginResponseHandler;
import demo.im.client.handler.MessageResponseHandler;
import demo.im.client.handler.QuitGroupResponseHandler;
import demo.im.codec.PacketDecoder;
import demo.im.codec.PacketEncoder;
import demo.im.codec.Spliter;
import demo.im.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(b, "localhost", 8080, MAX_RETRY);
    }

    private static void connect(Bootstrap b, String host, int port, int retry) {
        b.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("connect success. host: {}, port: {}", host, port);
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                log.info("retry is used, give up connect.");
            } else {
                int order = MAX_RETRY - retry;
                int delay = 1 << order;
                log.info("connect fail, retry :{}", order);
                b.config().group().schedule(() -> connect(b, host, port + 1, retry - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        final Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
