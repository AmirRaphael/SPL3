package bgu.spl.net.messages;

import bgu.spl.net.Database;
import bgu.spl.net.User;
import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

public class STUDENTREG extends Message {
    private String username;
    private String password;


    public STUDENTREG(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Message execute(BGRSProtocol protocol) {
        if(db.addUser(username,password,false)){
            return new ACK();
        }
        else return new ERR();
    }
}
