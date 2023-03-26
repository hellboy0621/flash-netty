package demo.im.client.handler;

import demo.im.protocol.response.LoginResponsePacket;
import demo.im.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.getSuccess()) {
            // 客户端绑定登陆成功的标识位
            LoginUtil.markAsLogin(ctx.channel());
            log.info("client login success");
        } else {
            log.error("login fail, the reason is {}", loginResponsePacket.getReason());
        }
    }
}
