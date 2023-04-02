package demo.im.server.handler;

import demo.im.protocol.request.ListGroupMembersRequestPacket;
import demo.im.protocol.response.ListGroupMembersResponsePacket;
import demo.im.session.Session;
import demo.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.List;

@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    protected ListGroupMembersRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {
        // 1 获取群的 ChannelGroup
        String groupId = listGroupMembersRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        // 2 遍历群成员的 channel 对应的 session，构造群成员信息
        List<Session> sessions = channelGroup.stream()
                .map(SessionUtil::getSession)
                .toList();

        // 3 构造获取群成员列表响应，写回客户端
        ListGroupMembersResponsePacket listGroupMembersResponsePacket = ListGroupMembersResponsePacket.builder()
                .sessions(sessions)
                .groupId(groupId)
                .build();
        ctx.writeAndFlush(listGroupMembersResponsePacket);
    }
}
