package demo.im.server;

import demo.im.codec.PacketDecoder;
import demo.im.codec.PacketEncoder;
import demo.im.codec.Spliter;
import demo.im.server.handler.AuthHandler;
import demo.im.server.handler.CreateGroupRequestHandler;
import demo.im.server.handler.JoinGroupRequestHandler;
import demo.im.server.handler.LoginRequestHandler;
import demo.im.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(new MessageRequestHandler());
                        ch.pipeline().addLast(new CreateGroupRequestHandler());
                        ch.pipeline().addLast(new JoinGroupRequestHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        bind(b, 8080);
    }

    private static void bind(final ServerBootstrap b, final int port) {
        b.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("port[{}] bind success.", port);
            } else {
                log.error("port[{}] bind fail.", port);
                bind(b, port + 1);
            }
        });
    }
}
