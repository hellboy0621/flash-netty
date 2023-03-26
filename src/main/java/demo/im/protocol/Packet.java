package demo.im.protocol;

import lombok.Builder;
import lombok.Data;

@Data
public abstract class Packet {

    private Byte version = 1;

    /**
     * 指令
     *
     * @return
     */
    public abstract Byte getCommand();
}
