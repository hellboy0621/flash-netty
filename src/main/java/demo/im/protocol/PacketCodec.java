package demo.im.protocol;

import cn.hutool.core.map.MapUtil;
import demo.im.protocol.command.Command;
import demo.im.protocol.request.CreateGroupRequestPacket;
import demo.im.protocol.request.GroupMessageRequestPacket;
import demo.im.protocol.request.JoinGroupRequestPacket;
import demo.im.protocol.request.ListGroupMembersRequestPacket;
import demo.im.protocol.request.LoginRequestPacket;
import demo.im.protocol.request.LogoutRequestPacket;
import demo.im.protocol.request.MessageRequestPacket;
import demo.im.protocol.request.QuitGroupRequestPacket;
import demo.im.protocol.response.CreateGroupResponsePacket;
import demo.im.protocol.response.GroupMessageResponsePacket;
import demo.im.protocol.response.JoinGroupResponsePacket;
import demo.im.protocol.response.ListGroupMembersResponsePacket;
import demo.im.protocol.response.LoginResponsePacket;
import demo.im.protocol.response.LogoutResponsePacket;
import demo.im.protocol.response.MessageResponsePacket;
import demo.im.protocol.response.QuitGroupResponsePacket;
import demo.im.serialize.Serializer;
import demo.im.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x12345678;
    private final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;
    private final Map<Byte, Serializer> SERIALIZER_MAP;

    public static final PacketCodec INSTANCE = new PacketCodec();

    private PacketCodec() {
        PACKET_TYPE_MAP = MapUtil.newHashMap();
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT_REQUEST, LogoutRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGOUT_RESPONSE, LogoutResponsePacket.class);

        SERIALIZER_MAP = MapUtil.newHashMap();
        JsonSerializer jsonSerializer = new JsonSerializer();
        SERIALIZER_MAP.put(jsonSerializer.getSerializerAlgorithm(), jsonSerializer);
    }

    public void encode(ByteBuf out, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        out.writeInt(MAGIC_NUMBER);
        out.writeByte(packet.getVersion());
        out.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        out.writeByte(packet.getCommand());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int len = byteBuf.readInt();

        // 数据包
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        log.error("requestType or serializer is null, please check PACKET_TYPE_MAP and SERIALIZER_MAP");
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return PACKET_TYPE_MAP.get(command);
    }
}
