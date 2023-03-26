package demo.im.serialize;

import demo.im.serialize.impl.JsonSerializer;

public interface Serializer {

    byte JSON_SERIALIZER = 1;
    Serializer DEFAULT = new JsonSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object obj);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
