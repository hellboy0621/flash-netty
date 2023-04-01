package demo.im.util;

import java.util.UUID;

public final class IdUtil {

    public static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
