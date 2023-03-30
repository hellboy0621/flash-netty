package demo.im.client.handler;

import demo.im.protocol.response.LoginResponsePacket;
import demo.im.session.Session;
import demo.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("client channel closed by server");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.getSuccess()) {
            // 客户端绑定登陆成功的标识位
            log.info("client[{}] login success, userId : {}", loginResponsePacket.getUserName(), loginResponsePacket.getUserId());
            Session session = Session.builder()
                    .userId(loginResponsePacket.getUserId())
                    .userName(loginResponsePacket.getUserName())
                    .build();
            SessionUtil.bindSession(session, ctx.channel());
        } else {
            log.error("login fail, the reason is {}", loginResponsePacket.getReason());
        }
    }
}
