package demo.im.protocol.response;

import demo.im.protocol.Packet;
import demo.im.protocol.command.Command;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEARTBEAT_RESPONSE;
    }
}
