package Actor;
import java.util.*;

public class Student extends Person {
    List<Course> registeredCourses;

    public Course findSubject(String code){return new Course();}
    public void registerSubject(Course course){}
    public void dropSubject(Course course){}
    public List<Course> displayListOfSubjects(){return new ArrayList<>();}
}
