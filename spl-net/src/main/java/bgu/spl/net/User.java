package bgu.spl.net;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class User {
    private String username;
    private String password;
    private TreeMap<Integer, Course> courseMap;
    private HashSet<Short> courseNums;
    private boolean isAdmin;
    private boolean isLoggedIn;

    public User(String username, String password,boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.courseMap = new TreeMap<>();
        this.courseNums = new HashSet<>();
        this.isLoggedIn = false;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void addCourse(Course course) {
        courseNums.add(course.getCourseNum());
        courseMap.put(course.getOrderNum(), course);
    }

    public boolean removeCourse(Course course) {
        boolean removedFromSet = courseNums.remove(course.getCourseNum());
        Course removedFromTree = courseMap.remove(course.getOrderNum());
        return (removedFromSet && removedFromTree != null);
    }

    public TreeMap<Integer, Course> getCourseMap() {
        return courseMap;
    }

    public void setCourseMap(TreeMap<Integer, Course> courseMap) {
        this.courseMap = courseMap;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean hasKdamCourses(List<Short> kdams){
        for (Short kdam : kdams){
            if (!courseNums.contains(kdam)) return false;
        }
        return true;
    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public HashSet<Short> getCourseNums() {
        return courseNums;
    }

    private String coursesToString() {
        String output = "[";
        for (Integer key : courseMap.keySet()) {
            output += courseMap.get(key).getCourseNum() + ", ";
        }
        if (!courseMap.isEmpty()) {
            output = output.substring(0, output.length() -2);
        }
        return output + "]";
    }

    @Override
    public String toString() {
        return "Student: " + username + "\n" +
                "Courses: " + coursesToString();
    }
}
