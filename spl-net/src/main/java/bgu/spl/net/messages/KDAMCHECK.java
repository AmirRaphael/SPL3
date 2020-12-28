package bgu.spl.net.messages;

import bgu.spl.net.Course;
import bgu.spl.net.User;
import bgu.spl.net.api.Message;
import bgu.spl.net.srv.BGRSProtocol;

import java.util.List;

public class KDAMCHECK extends Message {
    private short courseNum;

    @Override
    public Message execute(BGRSProtocol protocol) {
        List<Short> kdams = db.getKdamCourses(courseNum);
        if (kdams!=null) return new ACK();//TODO: THis is SHit
        return new ERR();
    }
}
