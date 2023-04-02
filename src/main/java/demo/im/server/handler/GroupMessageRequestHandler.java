package demo.im.server.handler;

import demo.im.protocol.request.GroupMessageRequestPacket;
import demo.im.protocol.response.GroupMessageResponsePacket;
import demo.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    protected GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        // 构造群聊消息的响应
        String groupId = groupMessageRequestPacket.getGroupId();
        GroupMessageResponsePacket groupMessageResponsePacket = GroupMessageResponsePacket.builder()
                .fromGroupId(groupId)
                .message(groupMessageRequestPacket.getMessage())
                .fromUser(SessionUtil.getSession(ctx.channel()))
                .build();

        // 获取群聊对应的 ChannelGroup，写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
