package bgu.spl.net.messages;

import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class STUDENTSTAT extends Message {
    private String username;

    public STUDENTSTAT(String username) {
        super(Short.parseShort("8"));
        this.username = username;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        if (protocol.getUser() != null && protocol.getUser().isAdmin()) {
            String studentStat = db.getStudentStat(username);
            if (studentStat != null)
                return new ACK(attachment, msgOpcode); //TODO fuck this shit we need to do this.
        }
        return new ERR(msgOpcode);
    }
}
