package demo.im.server.handler;

import demo.im.protocol.request.MessageRequestPacket;
import demo.im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        log.info("receive message from client: {}", messageRequestPacket.getMessage());
        ctx.channel().writeAndFlush(receiveMessage(messageRequestPacket));
    }

    private static MessageResponsePacket receiveMessage(MessageRequestPacket messageRequestPacket) {
        // 回复消息
        return MessageResponsePacket.builder()
                .message("server response: [" + messageRequestPacket.getMessage() + "]")
                .build();
    }
}
