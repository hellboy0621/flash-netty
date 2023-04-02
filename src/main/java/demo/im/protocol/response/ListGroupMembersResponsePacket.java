package demo.im.protocol.response;

import demo.im.protocol.Packet;
import demo.im.protocol.command.Command;
import demo.im.session.Session;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ListGroupMembersResponsePacket extends Packet {
    private String groupId;
    private List<Session> sessions;

    @Override
    public Byte getCommand() {
        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
