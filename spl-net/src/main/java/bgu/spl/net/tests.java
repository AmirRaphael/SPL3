package bgu.spl.net;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class tests {
    public static void main(String[] args) {
        TreeSet<Course> courses = new TreeSet<>(Comparator.comparingInt(Course::getOrderNum));
        courses.add(new Course((short) 15,"blabla",null,32,43));
        Set<Short> nums = courses.stream().map(Course::getCourseNum).collect(Collectors.toSet());
        System.out.println(nums);


    }
}
