package bgu.spl.net.messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class ISREGISTERED extends Message {
    private short courseNum;

    public ISREGISTERED(short courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        if (!protocol.getUser().isAdmin()){
            Boolean isRegistered = db.isRegistered(courseNum,protocol.getUser());
            if (isRegistered!=null){
                String output = isRegistered ? "REGISTERED":"NOT REGISTERED";
                return new ACK();
            }
        }
        return new ERR();
    }
}
