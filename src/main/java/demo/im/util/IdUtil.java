package demo.im.util;

import java.util.UUID;

public final class IdUtil {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
