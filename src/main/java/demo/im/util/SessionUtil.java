package demo.im.util;

import demo.im.attribute.Attributes;
import demo.im.session.Session;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionUtil {

    private static final Map<String, Channel> userIdToChannel = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdToChannel.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdToChannel.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            log.info("{} logout", session);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return getSession(channel) != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdToChannel.get(userId);
    }

}
