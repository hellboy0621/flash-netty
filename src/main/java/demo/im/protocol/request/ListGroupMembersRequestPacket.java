package demo.im.protocol.request;

import demo.im.protocol.Packet;
import demo.im.protocol.command.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ListGroupMembersRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
