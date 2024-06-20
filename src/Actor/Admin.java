package Actor;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.validation.Schema;

// import javax.security.auth.Course;
// public class AcademicOffice {
//     public ArrayList<Subject> listSubjectsToOfferEachSemester(){return new ArrayList<>();}
//     public void dropSubjectOrCourseIfBelowMin(Subject subject){}
//     public void confirmCourseRegistration(Student student, Lecturer lecturer){}
//     public void closeSubjectAtEndOfSemester(Subject subject){}
// }



public class Admin extends Person {
    private static ArrayList<Subject> subjectList;
    private static ArrayList<Student> studentsList;
    private static ArrayList<Lecturer> lecturerList;

    private static HashMap<String,Subject> subjectHashMap;
    private static HashMap<String,Student> studentHashMap;
    private static HashMap<String,Lecturer> lecturerHashMap;

    private static HashMap<Student,Subject> studentRegisterSubjectHashMap;
    
    static{
        subjectList=new ArrayList<Subject>();
        studentsList=new ArrayList<Student>();
        lecturerList=new ArrayList<Lecturer>();
        
        subjectHashMap=new HashMap<String,Subject>();
        studentHashMap=new HashMap<String,Student>();
        lecturerHashMap=new HashMap<String,Lecturer>();

        studentRegisterSubjectHashMap=new HashMap<Student,Subject>();
        try {
            readStudentList();
            readLecturerList();
            readSubjectList();
            
            subjectArrListToHashMap();
            studentArrListToHashMap();
            lecturerArrListToHashMap();

            readStudentRegSubject();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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


    // TODO 5. Confirm Course Registrations
    public void confirmCourseRegistrations() {
        System.out.println("***********************************************************************");
        System.out.println("                    CONFIRM COURSE REGISTRATION");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "COURSE CODE");
        System.out.printf("%-20s%-32s\n", "\t--------","-----------------------------");
        for (Map.Entry<Student, Subject> entry : studentRegisterSubjectHashMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        
        System.out.print("\n\n\t\tENTER Matriks NO : ");
        Scanner sc=new Scanner(System.in);
        int wait=sc.nextInt();
        

        
        //DO NOT CLOSE SCANNER BROSKI
        
    }


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

    //TODO 8. Close Subjects
    public void closeSubjects() {
        listSubjects();
        Scanner sc=new Scanner(System.in);
        System.out.print("Subject to close :");
        int temp=sc.nextInt();
        sc.next();
        
        
        sc.close();
    }



    private static void readStudentList() throws IOException{
        Scanner inpFile = new Scanner(new File("src/studentList.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String matrikxno = inpFile.next();
            String name = inpFile.nextLine();
            name = name.substring(1);
            Student student = new Student(matrikxno, name);
            studentsList.add(student);
        }
        inpFile.close();
    }
    private static void readLecturerList() throws IOException{
        Scanner inpFile = new Scanner(new File("src/lecturerList.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String matrikxno = inpFile.next();
            String name = inpFile.nextLine();
            name = name.substring(1);
            Lecturer lecturer = new Lecturer(name, matrikxno);
            lecturerList.add(lecturer);
        }
        inpFile.close();
    }
    private static void readSubjectList() throws IOException{
        Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String code = inpFile.next();
            int creditHr = inpFile.nextInt();
            boolean flag = inpFile.nextBoolean();
            String name = inpFile.nextLine();
            name = name.substring(1);
            Subject subject = new Subject(code,name,flag,creditHr );
            subjectList.add(subject);
        }
        inpFile.close();
    }

    private static void subjectArrListToHashMap(){
        for(Subject s:subjectList){
            subjectHashMap.put(s.getCode(), s);
        }
    }
    private static void studentArrListToHashMap(){
        for(Student s:studentsList){
            studentHashMap.put(s.getId(),s);
        }
    }
    private static void lecturerArrListToHashMap(){
        for(Lecturer l:lecturerList){
            lecturerHashMap.put(l.getId(), l);
        }
    }

    private static void readStudentRegSubject() throws IOException{
        Scanner inpFile = new Scanner(new File("src/studentRegisterSubject.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String matriksNo = inpFile.next();
            String subjectCode = inpFile.nextLine();
            Student tempStud=studentHashMap.get(matriksNo);
            Subject tempSubject=subjectHashMap.get(subjectCode);

            studentRegisterSubjectHashMap.put(tempStud,tempSubject );
            
            
        }
        inpFile.close();
    }



}