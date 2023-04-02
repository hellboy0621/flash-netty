package demo.im.protocol.response;

import demo.im.protocol.Packet;
import demo.im.protocol.command.Command;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class LogoutResponsePacket extends Packet {
    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
