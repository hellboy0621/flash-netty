package demo.im.server.handler;

import demo.im.protocol.request.MessageRequestPacket;
import demo.im.protocol.response.MessageResponsePacket;
import demo.im.session.Session;
import demo.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        log.info("receive message from client: {}", messageRequestPacket.getMessage());
        // 1 获得消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        // 2 通过消息发送方的会话信息，构造要发送的消息
        MessageResponsePacket messageResponsePacket = MessageResponsePacket.builder()
                .fromUserId(session.getUserId())
                .fromUserName(session.getUserName())
                .message("server response: [" + messageRequestPacket.getMessage() + "]")
                .build();

        // 3 获得消息接收方的 Channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 4 将消息发送给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.error("[{}] does not online, send fail", messageRequestPacket.getToUserId());
        }
    }

}
