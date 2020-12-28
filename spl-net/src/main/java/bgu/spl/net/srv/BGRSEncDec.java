package bgu.spl.net.srv;

import bgu.spl.net.api.Message;
import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class BGRSEncDec implements MessageEncoderDecoder<Message> {

    private ByteBuffer opBuffer = ByteBuffer.allocate(2);
    private byte[] bytes = null;
    private int len = 0;
    private short opcode = -1;


    @Override
    public Message decodeNextByte(byte nextByte) {
        if(bytes==null) {
            opBuffer.put(nextByte);
            if (!opBuffer.hasRemaining()) {
                opBuffer.flip();
                bytes = new byte[1 << 10]; //start with 1k
                len = 0;
                opcode = bytesToShort(opBuffer.array());
                opBuffer.clear();

            }
            switch (opcode){
                case 4:
                case 11:
                    return popMsg(opcode);
                default:
                    return null;
            }
        }

        else { // if the program gets here, then 'nextByte' is a byte that is *not opcode byte*
            switch (opcode){

            }
        }

        return null;

    }

    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }
    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len++] = nextByte;
    }
    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
