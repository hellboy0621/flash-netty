package demo.im.protocol.response;

import demo.im.protocol.Packet;
import demo.im.protocol.command.Command;
import demo.im.session.Session;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class GroupMessageResponsePacket extends Packet {
    private String fromGroupId;
    private String message;
    private Session fromUser;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
