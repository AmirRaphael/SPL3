package bgu.spl.net.messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class STUDENTSTAT extends Message {
    private String username;

    public STUDENTSTAT(String username) {
        this.username = username;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        if (protocol.getUser().isAdmin()){
            String studentStat = db.getStudentStat(username);
            if (studentStat!=null) return new ACK();//TODO fuck this shit we need to do this .
        }
        return new ERR();
    }
}
