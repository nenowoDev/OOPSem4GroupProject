package Actor;

import java.util.*;
import java.io.*;

// public class Subject {
//     String code;
//     String name;
//     int creditHour;
//     ArrayList<Student> registeredStudents;
//     Lecturer lecturer;
//     boolean confirm;
// }

public class Subject {
    private String code;
    private String name;
    private int creditHour;

    // private Vector<Student>registeredStudents;
    // private Lecturer lecturer;
    // boolean confirm;

    // Constructor, getters, setters, etc.

    public Subject(String code, String name, int creditHour) {
        this.code = code;
        this.name = name;
        this.creditHour = creditHour;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public void readFile(Vector<Subject> subjectList) throws IOException {
        Scanner inpFile = new Scanner(new File("subjectList.txt"));

        while (inpFile.hasNext()) {
            code = inpFile.next();
            creditHour = inpFile.nextInt();
            name = inpFile.nextLine();

            Subject subject = new Subject(code, name, creditHour);
            subjectList.add(subject);
        }

        inpFile.close();
    }

    public void display(Vector<Subject> subjectList) {
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF COURSES");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-12s%-34s%5s\n", "\tCODE", "COURSE NAME", "CREDIT HOUR");
        System.out.printf("%-12s%-34s%5s\n", "\t--------","-------------------------------", "-----------");
        for (Subject s : subjectList) {
            System.out.printf("\t%-10s%-35s%5d\n", s.getCode(), s.getName(), s.getCreditHour());
        }
        System.out.println();
    }

}
