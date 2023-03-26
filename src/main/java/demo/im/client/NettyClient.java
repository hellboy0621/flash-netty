package demo.im.client;

import demo.im.packet.MessageRequestPacket;
import demo.im.packet.PacketCodec;
import demo.im.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
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
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
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
                System.out.println("retry is used, give up connect.");
            } else {
                // 第几次重连
                int order = MAX_RETRY - retry + 1;
                int delay = 1 << order;
                System.out.println(LocalDateTime.now() + " connect fail, retry: " + order);
                b.config().group().schedule(() ->
                        connect(b, host, port + 1, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    log.info("input message send to server: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    MessageRequestPacket messageRequestPacket = MessageRequestPacket.builder()
                            .message(line)
                            .build();
                    ByteBuf byteBuf = PacketCodec.INSTANCE.encode(messageRequestPacket);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }
}
