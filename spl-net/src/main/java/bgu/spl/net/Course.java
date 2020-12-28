package bgu.spl.net;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Course {
    private short courseNum;
    private String courseName;
    private List<Short> kdamCoursesList;
    private int numOfMaxStudents;
    private int orderNum;
    private TreeSet<String> studentsRegistered;

    public Course(short courseNum, String courseName, List<Short> kdamCoursesList, int numOfMaxStudents, int orderNum) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.orderNum = orderNum;
        studentsRegistered = new TreeSet<>();
    }

    @Override
    public String toString() {
        int available = numOfMaxStudents - studentsRegistered.size();
        String output = "Course: (" + courseNum + ") " + courseName + "\n"
                + "Seats Available: " + available + "/" + numOfMaxStudents + "\n"
                + "Students Registered: " + studentsRegistered.toString();
        return output;
    }
    public int seatsAvailable(){
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

    public void setCourseNum(short courseNum) {
        this.courseNum = courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Short> getKdamCoursesList() {
        return kdamCoursesList;
    }

    public void setKdamCoursesList(List<Short> kdamCoursesList) {
        this.kdamCoursesList = kdamCoursesList;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public void setNumOfMaxStudents(int numOfMaxStudents) {
        this.numOfMaxStudents = numOfMaxStudents;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public boolean unReg(User user) {
        return studentsRegistered.remove(user.getUsername());
    }
}
