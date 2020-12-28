package bgu.spl.net.messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class UNREGISTER extends Message {
    short courseNum;

    public UNREGISTER(short courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        if (!protocol.getUser().isAdmin()){
            boolean unreg = db.unReg(courseNum,protocol.getUser());
        }
    }
}
