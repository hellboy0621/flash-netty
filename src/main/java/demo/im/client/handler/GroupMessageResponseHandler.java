package demo.im.client.handler;

import demo.im.protocol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {
        log.info("receive from group[{}] user[{}] message: {}",
                groupMessageResponsePacket.getFromGroupId(),
                groupMessageResponsePacket.getFromUser(),
                groupMessageResponsePacket.getMessage());
    }
}
