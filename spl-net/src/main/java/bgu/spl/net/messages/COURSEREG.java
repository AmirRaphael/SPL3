package bgu.spl.net.messages;

import bgu.spl.net.User;
import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class COURSEREG extends Message {
    private short courseNum;

    @Override
    public Message execute(BGRSProtocol protocol) {
        User user = protocol.getUser();
        if (db.courseReg(courseNum, user)) {
            return new ACK();
        }
        return new ERR();
    }
}
