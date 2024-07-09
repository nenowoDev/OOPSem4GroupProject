package Actor;

import java.util.Scanner;

public interface StudentBehavior {
    
    void searchSubject(Scanner sc);
    void registerSubject(String studentID);
    void dropSubject(String studentID);
    void listSubjects();
    void listStudentSubjects(String studentID);

}
