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

    public short getCourseNum() {
        return courseNum;
    }

    public List<Short> getKdamCoursesList() {
        return kdamCoursesList;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public boolean isRegistered(String name) {
        return studentsRegistered.contains(name);
    }

    /**
     * Registers the specified {@link User} to this Course.
     *
     * @param username the username of the specified {@code User}.
     * @return {@code true} if the user was registered successfully,
     * {@code false} if there is no seat available for the user.
     */
    public boolean addStudent(String username) {
        if (seatsAvailable.tryAcquire()) {
            if (studentsRegistered.add(username)) {
                return true;
            } else {
                seatsAvailable.release();
            }
        }
        return false;
    }

    /**
     * Unregisters the specified {@link User} from this Course.
     *
     * @param username the username of the specified {@code User}.
     * @return {@code true} if the user was unregistered successfully,
     * {@code false} otherwise.
     */
    public boolean removeStudent(String username) {
        boolean removed = studentsRegistered.remove(username);
        if (removed)
            seatsAvailable.release();
        return removed;
    }

    @Override
    public String toString() {
        int available = numOfMaxStudents - studentsRegistered.size();
        return "Course: (" + courseNum + ") " + courseName + "\n"
                + "Seats Available: " + available + "/" + numOfMaxStudents + "\n"
                + "Students Registered: " + studentsRegistered.toString().replace(" ","");
    }
}
