package bgu.spl.net.messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class ACK extends Message {
    private String attachment;
    private short msgOpcode;

    public ACK(String attachment, short msgOpcode) {
        super(Short.parseShort("12"));
        this.attachment = attachment;
        this.msgOpcode = msgOpcode;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        return null;
    }

    @Override
    public String toString() {
        return Short.toString(opcode) + Short.toString(msgOpcode) + attachment + "\0";

    }
}
