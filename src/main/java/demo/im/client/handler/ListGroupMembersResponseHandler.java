package demo.im.client.handler;

import demo.im.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        log.info("group[{}] contains: {}", listGroupMembersResponsePacket.getGroupId(),
                listGroupMembersResponsePacket.getSessions());
    }
}
