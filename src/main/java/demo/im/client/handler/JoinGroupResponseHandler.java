package demo.im.client.handler;

import demo.im.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    public static final JoinGroupResponseHandler INSTANCE = new JoinGroupResponseHandler();

    protected JoinGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if (joinGroupResponsePacket.isSuccess()) {
            log.info("join group [{}] success.", joinGroupResponsePacket.getGroupId());
        } else {
            log.error("join group [{}] fail. reason: {}", joinGroupResponsePacket.getGroupId(),
                    joinGroupResponsePacket.getReason());
        }
    }
}
