package bgu.spl.net;

import java.util.List;
import java.util.TreeSet;

public class Course {
    private int courseNum;
    private String courseName;
    List<Integer> kdamCoursesList;
    private int numOfMaxStudents;
    private int orderNum;
    TreeSet<String> studentsRegistered;

    public Course(int courseNum, String courseName, List<Integer> kdamCoursesList, int numOfMaxStudents, int orderNum) {
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

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Integer> getKdamCoursesList() {
        return kdamCoursesList;
    }

    public void setKdamCoursesList(List<Integer> kdamCoursesList) {
        this.kdamCoursesList = kdamCoursesList;
    }

    public int getNumOfMaxStudents() {
        return numOfMaxStudents;
    }

    public void setNumOfMaxStudents(int numOfMaxStudents) {
        this.numOfMaxStudents = numOfMaxStudents;
    }
}
