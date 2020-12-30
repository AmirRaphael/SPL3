package bgu.spl.net;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Course {
    private final short courseNum;
    private final String courseName;
    private final List<Short> kdamCoursesList;
    private final int numOfMaxStudents;
    private final int orderNum;
    private TreeSet<String> studentsRegistered;

    public Course(short courseNum, String courseName, List<Short> kdamCoursesList, int numOfMaxStudents, int orderNum) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.orderNum = orderNum;
        studentsRegistered = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    }

    @Override
    public String toString() {
        int available = numOfMaxStudents - studentsRegistered.size();
        String output = "Course: (" + courseNum + ") " + courseName + "\n"
                + "Seats Available: " + available + "/" + numOfMaxStudents + "\n"
                + "Students Registered: " + studentsRegistered.toString();
        return output;
    }
    public synchronized int seatsAvailable(){
        return numOfMaxStudents-studentsRegistered.size();
    }

    public boolean isRegistered(String name){
        return studentsRegistered.contains(name);
    }

    public void addStudent(String name){
        studentsRegistered.add(name);
    }

    public short getCourseNum() {
        return courseNum;
    }


    public List<Short> getKdamCoursesList() {
        return kdamCoursesList;
    }



    public int getOrderNum() {
        return orderNum;
    }

    public boolean unReg(User user) {
        return studentsRegistered.remove(user.getUsername());
    }
}
