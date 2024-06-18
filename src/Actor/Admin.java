package Actor;
import java.io.File;
import java.io.IOException;
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
        studentsList=new ArrayList<Student>();
        try {
            readStudentList();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF STUDENTS");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "NAME");
        System.out.printf("%-20s%-32s\n", "\t--------","-----------------------------");
        for(Student s:studentsList){
            System.out.printf("\t%-20s%-32s\n",s.getId(),s.getName());
        }
        System.out.println();
    
    }


    //7. List Registered Lecturers
    public void listRegisteredLecturers() {
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF LECTURERS");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-12s%-32s\n", "\tSTAFF NO", "NAME");
        System.out.printf("%-12s%-32s\n", "\t--------","-----------------------------");
        for(Lecturer l:lecturerList){
            System.out.printf("%-12s%-32s\n",l.getId(),l.getName());
        }
        System.out.println();
        
    }


    //8. Close Subjects
    public void closeSubjects(Subject s) {
        
    }



    public void readStudentList() throws IOException{
        Scanner inpFile = new Scanner(new File("src/studentList.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String matrikxno = inpFile.next();
            String name = inpFile.nextLine();
            name = name.substring(1);

            Student student = new Student(name, matrikxno);
            studentsList.add(student);
        }
        inpFile.close();
    }
}