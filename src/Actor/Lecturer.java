package Actor;
import java.util.*;

// import javax.security.auth.Course;
// public class Lecturer extends Person{
//     ArrayList<Subject> coursessToTeach;
//     public void chooseSubjectToTeach(Subject subject){}
//     public void dropSubject(Subject subject){} 
// }



public class Lecturer extends Person {
    private Vector<Subject> subjectsToTeach;
    
    Lecturer(String name, String id) {
        super(id, name);
    }

    //1. View Subject Details
    public void viewSubjectDetails() {}



    //2. Choose Subject to Teach
    public void chooseSubjectToTeach() {}



    //3. Drop Subject
    public void dropSubject() {}



    //4. Subject Student List
    public void subjectStudentList() {}


}
