package Actor;
import java.util.*;

public class Student extends Person {
    List<Subject> registeredSubjects;

    public Subject findSubject(String code){return new Subject();}
    public void registerSubject(Subject subject){}
    public void dropSubject(Subject subject){}
    public List<Subject> displayListOfSubjects(){return new ArrayList<>();}
}
