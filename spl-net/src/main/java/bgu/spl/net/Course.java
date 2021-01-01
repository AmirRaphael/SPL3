package bgu.spl.net;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Semaphore;

public class Course {
    private final short courseNum;
    private final String courseName;
    private final List<Short> kdamCoursesList;
    private final int numOfMaxStudents;
    private final int orderNum;
    private final Semaphore seatsAvailable;
    private final ConcurrentSkipListSet<String> studentsRegistered;

    public Course(short courseNum, String courseName, List<Short> kdamCoursesList, int numOfMaxStudents, int orderNum) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCoursesList = kdamCoursesList;
        this.numOfMaxStudents = numOfMaxStudents;
        this.orderNum = orderNum;
        this.studentsRegistered = new ConcurrentSkipListSet<>(String.CASE_INSENSITIVE_ORDER);
        this.seatsAvailable = new Semaphore(numOfMaxStudents);
    }

    @Override
    public String toString() {
        int available = numOfMaxStudents - studentsRegistered.size();
        return "Course: (" + courseNum + ") " + courseName + "\n"
                + "Seats Available: " + available + "/" + numOfMaxStudents + "\n"
                + "Students Registered: " + studentsRegistered.toString();
    }

    public boolean isRegistered(String name){
        return studentsRegistered.contains(name);
    }

    public boolean addStudent(String name){
        if (seatsAvailable.tryAcquire()){
           if (studentsRegistered.add(name)){
               return true;
           }
           else {
               seatsAvailable.release();
           }
        }
        return false;
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
        boolean removed = studentsRegistered.remove(user.getUsername());
        if (removed)
            seatsAvailable.release();
        return removed;
    }
}
