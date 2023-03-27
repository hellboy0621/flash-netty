package demo.im.server.handler;

import demo.im.protocol.request.LoginRequestPacket;
import demo.im.protocol.response.LoginResponsePacket;
import demo.im.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        log.info("client start login");
        ctx.channel().writeAndFlush(login(ctx, loginRequestPacket));
    }

    private LoginResponsePacket login(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        LoginResponsePacket loginResponsePacket = LoginResponsePacket.builder()
                .build();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            log.info("client login success.");
            // 校验成功
            LoginUtil.markAsLogin(ctx.channel());
            loginResponsePacket.setSuccess(true);
        } else {
            log.info("client login fail.");
            // 校验失败
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("account username or password error.");
        }
        return loginResponsePacket;
    }

    private boolean valid(demo.im.protocol.request.LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
