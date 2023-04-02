package demo.im.protocol.request;

import demo.im.protocol.Packet;
import demo.im.protocol.command.Command;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
