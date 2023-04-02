package demo.im.server.handler;

import demo.im.protocol.request.CreateGroupRequestPacket;
import demo.im.protocol.response.CreateGroupResponsePacket;
import demo.im.util.IdUtil;
import demo.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    protected CreateGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIds = createGroupRequestPacket.getUserIds();

        List<String> userNames = new ArrayList<>();
        // 1 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIds) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNames.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 3 创建群聊创建结果的响应
        String groupId = IdUtil.randomId();
        final CreateGroupResponsePacket createGroupResponsePacket = CreateGroupResponsePacket.builder()
                .groupId(groupId)
                .success(true)
                .userNames(userNames)
                .build();

        // 4 给每个客户端发送拉群通知
        // channelGroup.writeAndFlush(createGroupRequestPacket); 这里找了好长时间，原来是因为把 request 返回了，怪不得打断点都不进 responseHandler
        channelGroup.writeAndFlush(createGroupResponsePacket);

        log.info("group create success, id : {}", createGroupResponsePacket.getGroupId());
        log.info("group contains userNames : {}", userNames);

        SessionUtil.bindGroupSession(groupId, channelGroup);
    }
}
