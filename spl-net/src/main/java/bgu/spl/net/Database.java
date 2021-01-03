package bgu.spl.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    //Todo: check thread-safety

    private final ConcurrentHashMap<Short, Course> courseMap;
    private final ConcurrentHashMap<String, User> userMap;
    private final List<Short> courseList;
    private static class SingletonClassHolder {
        static final Database instance = new Database();
    }

    //to prevent user from creating new Database
    private Database() {
        courseMap = new ConcurrentHashMap<>();
        userMap = new ConcurrentHashMap<>();
        courseList = new ArrayList<>();
        initialize("/Users/amirzaushnizer/Desktop/SPL3/spl-net/Courses.txt");
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return SingletonClassHolder.instance;
    }

    /**
     * loads the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(coursesFilePath))) {
            int counter = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stringArr = line.split("\\|");
                short courseNum = Short.parseShort(stringArr[0]);
                String courseName = stringArr[1];
                List<Short> kdamList = parseList(stringArr[2]);
                int maxStudents = Integer.parseInt(stringArr[3]);
                courseMap.put(courseNum, new Course(courseNum, courseName, kdamList, maxStudents, counter++));
                courseList.add(courseNum); //Added in order of courses in the input file
            }
            for (Course course : courseMap.values()) {
                course.getKdamCoursesList().sort(Comparator.comparing((item) -> courseList.indexOf(item)));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Short> parseList(String list) {
        if (list.equals("[]")) {
            return new ArrayList<>();
        }
        list = list.substring(1, list.length() - 1);
        String[] strings = list.split(",");
        List<Short> output = new ArrayList<>();
        for (String str : strings) {
            output.add(Short.parseShort(str));
        }
        return output;
    }


    public boolean addUser(String name, String password, boolean isAdmin) {
        User user =userMap.putIfAbsent(name, new User(name, password, isAdmin));
        return user==null;
    }

    public boolean login(String name, String password) {
        User user = userMap.get(name);
        // user == null iff this user does not exist in the DB
        if (user != null && user.verifyPassword(password)) {
            return user.login();
        }
        return false;
    }

    public User getUser(String name) {
        return userMap.get(name);
    }

    public void logout(String name) {
        userMap.get(name).logout();
    }

    public boolean courseReg(short courseNum, User user) {
        Course course = courseMap.get(courseNum);
        if (course != null && !user.isAdmin() && user.hasKdamCourses(course.getKdamCoursesList()) ) {
            if(course.addStudent(user.getUsername())){
                user.addCourse(course);
                return true;
            }
        }
        return false;
    }

    public List<Short> getKdamCourses(short courseNum) {
        Course course = courseMap.get(courseNum);
        if (course != null) {
            return course.getKdamCoursesList();
        }
        return null;
    }

    public String getCourseStat(short courseNum) {
        Course course = courseMap.get(courseNum);
        if (course != null)
            return course.toString();
        return null;
    }

    public String getStudentStat(String username) {
        User user = userMap.get(username);
        if (user != null)
            return user.toString();
        return null;
    }

    public Boolean isRegistered(short courseNum, User user) {
        Course course = courseMap.get(courseNum);
        if (course != null) {
            return course.isRegistered(user.getUsername());
        }
        return null;
    }

    public boolean unReg(short courseNum, User user) {
        Course course = courseMap.get(courseNum);
        if (course != null) {
            return course.unReg(user) && user.removeCourse(course);
        }
        return false;
    }


}