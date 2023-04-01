package demo.im.server.handler;

import demo.im.protocol.request.LoginRequestPacket;
import demo.im.protocol.response.LoginResponsePacket;
import demo.im.session.Session;
import demo.im.util.IdUtil;
import demo.im.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        log.info("client start login {}", loginRequestPacket.getUsername());
        LoginResponsePacket loginResponsePacket = LoginResponsePacket.builder()
                .build();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            // 校验成功
            loginResponsePacket.setSuccess(true);
            String userId = IdUtil.randomUserId();
            loginResponsePacket.setUserId(userId);
            loginResponsePacket.setUserName(loginRequestPacket.getUsername());
            log.info("client[{}] login success, userId : {}", loginRequestPacket.getUsername(), userId);
            Session session = Session.builder().userId(userId).userName(loginRequestPacket.getUsername()).build();
            SessionUtil.bindSession(session, ctx.channel());
        } else {
            // 校验失败
            log.info("client login fail.");
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("account username or password error.");
        }

        // ctx.writeAndFlush(loginResponsePacket); 这样功能就不正常了
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(demo.im.protocol.request.LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 用户断线之后取消绑定
        SessionUtil.unBindSession(ctx.channel());
    }
}
