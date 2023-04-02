package demo.im.client.handler;

import demo.im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();

    protected MessageResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        log.info("{} : {} -> {}", fromUserId, fromUserName,
                messageResponsePacket.getMessage());
    }
}
