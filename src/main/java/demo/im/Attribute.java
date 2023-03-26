package demo.im;

import io.netty.util.AttributeKey;

public interface Attribute {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
