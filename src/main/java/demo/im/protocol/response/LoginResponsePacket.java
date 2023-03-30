package demo.im.protocol.response;

import demo.im.protocol.command.Command;
import demo.im.protocol.Packet;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet {

    private Boolean success;
    private String reason;
    private String userId;
    private String userName;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
