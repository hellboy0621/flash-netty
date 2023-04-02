package demo.im.client.handler;

import demo.im.protocol.response.LogoutResponsePacket;
import demo.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    public static final LogoutResponseHandler INSTANCE = new LogoutResponseHandler();

    protected LogoutResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
