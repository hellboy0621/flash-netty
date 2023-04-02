package demo.im.server.handler;

import demo.im.protocol.request.QuitGroupRequestPacket;
import demo.im.protocol.response.QuitGroupResponsePacket;
import demo.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        // 1 获取群id对应的 ChannelGroup，然后将当前用户的 channel 移除
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket = QuitGroupResponsePacket.builder()
                .success(true)
                .groupId(groupId)
                .build();
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
