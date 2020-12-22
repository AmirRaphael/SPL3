package bgu.spl.net;

import java.util.TreeMap;

public class Student extends User {

    TreeMap<Integer, Course> courseMap;

    public Student(String username, String password) {
        super(username, password);
        courseMap = new TreeMap<>();
    }
}
