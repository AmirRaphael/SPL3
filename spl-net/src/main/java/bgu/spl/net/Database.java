package bgu.spl.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
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

    private ConcurrentHashMap<Integer, Course> courseMap;
    private ConcurrentHashMap<String,Student> studentMap;
    private ConcurrentHashMap<String,Admin> adminMap;

    private static class SingletonClassHolder {
        static final Database instance = new Database();
    }

    //to prevent user from creating new Database
    private Database() {
        courseMap = new ConcurrentHashMap<>();
        studentMap = new ConcurrentHashMap<>();
        adminMap = new ConcurrentHashMap<>();
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
        try(BufferedReader reader = new BufferedReader(new FileReader(coursesFilePath))) {
            int counter = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] stringArr = line.split("\\|");
                int courseNum = Integer.parseInt(stringArr[0]);
                String courseName = stringArr[1];
                List<Integer> kdamList = parseList(stringArr[2]);
                int maxStudents = Integer.parseInt(stringArr[3]);
                courseMap.put(courseNum, new Course(courseNum, courseName, kdamList, maxStudents, counter++));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Integer> parseList(String list) {
        if (list.equals("[]")) {
            return new ArrayList<>();
        }
        list = list.substring(1,list.length()-1);
        String[] strings = list.split(",");
        List<Integer> output = new ArrayList<>();
        for (String str : strings) {
            output.add(Integer.parseInt(str));
        }
        return output;
    }

}