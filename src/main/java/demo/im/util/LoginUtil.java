package demo.im.util;


import demo.im.Attribute;
import io.netty.channel.Channel;

public class LoginUtil {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attribute.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        io.netty.util.Attribute<Boolean> loginAttr = channel.attr(Attribute.LOGIN);
        return loginAttr.get() != null;
    }
}
