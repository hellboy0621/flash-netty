package demo.im.server.handler;

import demo.im.protocol.request.LoginRequestPacket;
import demo.im.protocol.response.LoginResponsePacket;
import demo.im.protocol.request.MessageRequestPacket;
import demo.im.protocol.response.MessageResponsePacket;
import demo.im.protocol.Packet;
import demo.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        if (packet instanceof LoginRequestPacket loginRequestPacket) {
            log.info("client start login");
            LoginResponsePacket loginResponsePacket = LoginResponsePacket.builder()
                    .build();
            if (valid(loginRequestPacket)) {
                log.info("client login success.");
                // 校验成功
                loginResponsePacket.setSuccess(true);
            } else {
                log.info("client login fail.");
                // 校验失败
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("account username or password error.");
            }

            ByteBuf loginResponseBytebuf = PacketCodec.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(loginResponseBytebuf);
        } else if (packet instanceof MessageRequestPacket messageRequestPacket) {
            log.info("receive message from client: {}", messageRequestPacket.getMessage());

            // 回复消息
            MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                    .message("server response: [" + messageRequestPacket.getMessage() + "]")
                    .build();
            ByteBuf messageResponseByteBuf = PacketCodec.INSTANCE.encode(messageResponsePacket);
            ctx.channel().writeAndFlush(messageResponseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
