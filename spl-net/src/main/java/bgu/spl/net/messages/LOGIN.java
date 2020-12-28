package bgu.spl.net.messages;

import bgu.spl.net.User;
import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class LOGIN extends Message {
    private String username;
    private String password;

    public LOGIN(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        boolean loginSuccessful = db.login(username, password);
        if (loginSuccessful) {
            protocol.setUser(db.getUser(username));
            return new ACK();
        }
        return new ERR();
    }
}
