package demo.im.client.handler;

import demo.im.protocol.request.LoginRequestPacket;
import demo.im.protocol.response.LoginResponsePacket;
import demo.im.protocol.response.MessageResponsePacket;
import demo.im.protocol.Packet;
import demo.im.protocol.PacketCodec;
import demo.im.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("client start login");

        LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .userId(UUID.randomUUID().toString())
                .username("flash")
                .password("pwd")
                .build();

        ByteBuf buffer = PacketCodec.INSTANCE.encode(loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf byteBuf) {
            Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
            if (packet instanceof LoginResponsePacket loginResponsePacket) {
                if (loginResponsePacket.getSuccess()) {
                    // 客户端绑定登陆成功的标识位
                    LoginUtil.markAsLogin(ctx.channel());
                    log.info("client login success");
                } else {
                    log.error("login fail, the reason is {}", loginResponsePacket.getReason());
                }
            } else if (packet instanceof MessageResponsePacket messageResponsePacket) {
                log.info("receive msg from server : {}", messageResponsePacket.getMessage());
            }
        }
    }
}
