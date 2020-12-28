package bgu.spl.net;

import java.util.HashSet;
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
}
