package demo.im.client.handler;

import demo.im.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    public static final QuitGroupResponseHandler INSTANCE = new QuitGroupResponseHandler();

    protected QuitGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        if (quitGroupResponsePacket.isSuccess()) {
            log.info("quit group [{}] success.", quitGroupResponsePacket.getGroupId());
        } else {
            log.error("quit group [{}] fail. reason: {}", quitGroupResponsePacket.getGroupId(),
                    quitGroupResponsePacket.getReason());
        }
    }
}
