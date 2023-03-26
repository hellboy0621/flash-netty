package demo.pratise;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // 01 02 03 04
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        // 01 02 03 04 00 00 00 0C
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // 01 02 03 04 00 00 00 0C 05
        buffer.writeBytes(new byte[]{5});
        print("writeBytes{(5)", buffer);

        // 01 02 03 04 00 00 00 0C 05 06
        buffer.writeBytes(new byte[]{6});
        print("writeBytes{(6)", buffer);

        // get 方法不会改变读写指针
        log.info("getByte(3) return: {}", buffer.getByte(3)); // 04 -> 4
        log.info("getShort(3) return: {}", buffer.getShort(3)); // 04 00 -> 00000100 00000000 -> 1 << 10 -> 1024
        log.info("getInt(3) return: {}", buffer.getInt(3)); // 04 00 00 00 -> 1 << 26 -> 67108864
        print("getByte()", buffer);

        // 01 02 03 04 00 00 00 0C 05 06 00
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // 01 02 03 04 00 00 00 0C 05 06
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }

    private static void print(String action, ByteBuf buffer) {
        log.info("after ---------- {} ----------", action);
        log.info("capacity(): {}", buffer.capacity());
        log.info("maxCapacity(): {}", buffer.maxCapacity());
        log.info("readerIndex(): {}", buffer.readerIndex());
        log.info("readableBytes(): {}", buffer.readableBytes());
        log.info("isReadable(): {}", buffer.isReadable());
        log.info("writerIndex(): {}", buffer.writerIndex());
        log.info("writableBytes(): {}", buffer.writableBytes());
        log.info("isWritable(): {}", buffer.isWritable());
        log.info("maxWritableBytes(): {}", buffer.maxWritableBytes());
    }
}
