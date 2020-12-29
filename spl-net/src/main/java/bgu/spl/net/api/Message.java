package bgu.spl.net.api;

import bgu.spl.net.Database;
import bgu.spl.net.User;
import bgu.spl.net.srv.BGRSProtocol;

public abstract class Message {
    private short opcode;
    protected Database db = Database.getInstance();

    public Message(short opcode) {
        this.opcode = opcode;
    }

    public abstract Message execute(BGRSProtocol protocol);

}
