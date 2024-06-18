package Actor;
import java.util.*;

// import javax.security.auth.Course;
// public class AcademicOffice {
//     public ArrayList<Subject> listSubjectsToOfferEachSemester(){return new ArrayList<>();}
//     public void dropSubjectOrCourseIfBelowMin(Subject subject){}
//     public void confirmCourseRegistration(Student student, Lecturer lecturer){}
//     public void closeSubjectAtEndOfSemester(Subject subject){}
// }



public class Admin extends Person {
    private ArrayList<Subject> subjectsOffered;
    private ArrayList<Student> studentsList;
    private ArrayList<Lecturer> lecturerList;
    
    
    public Admin(String id, String name) {
        super(id, name);
    }


    //1. List Subjects
    public void listSubjects() {}

    
    //2. Manage Subject Sections
    public void manageSubjectSections() {}


    //3. Set Student Capacity
    public void setStudentCapacity() {}


    //4. Drop Subject/Course
    public void dropSubjectCourse() {}


    //5. Confirm Course Registrations
    public void confirmCourseRegistrations() {}


    //6. List Registered Students
    public void listRegisteredStudents() {
        for(Student s:studentsList){
            System.out.println(s);
        }
    }


    //7. List Registered Lecturers
    public void listRegisteredLecturers() {
        for(Lecturer l:lecturerList){
            System.out.println(l);
        }
    }


    //8. Close Subjects
    public void closeSubjects() {}
}