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
    private int flag;

    // private ArrayList<Student>registeredStudents;
    // private Lecturer lecturer;
    // boolean confirm;

    // Constructor, getters, setters, etc.

    public Subject(String code, String name, int creditHour, int flag) {
        this.code = code;
        this.name = name;
        this.creditHour = creditHour;
        this.flag = flag;
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

    public int getFlag() {
        return flag;
    }

    public void readFile(ArrayList<Subject> subjectList) throws IOException {
        Scanner inpFile = new Scanner(new File("subjectList.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String code = inpFile.next();
            int creditHour = inpFile.nextInt();
            int flag = inpFile.nextInt();
            String name = inpFile.nextLine();
            name = name.substring(1);

            Subject subject = new Subject(code, name, creditHour, flag);
            subjectList.add(subject);
        }
        inpFile.close();
    }

    public void display(ArrayList<Subject> subjectList) {
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF COURSES");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-12s%-32s%5s\n", "\tCODE", "COURSE NAME", "CREDIT HOUR");
        System.out.printf("%-12s%-32s%5s\n", "\t--------","-----------------------------", "-----------");
        for (Subject s : subjectList) {
            System.out.printf("\t%-11s%-33s%5d\n", s.getCode(), s.getName(), s.getCreditHour());
        }
        System.out.println();
    }

}
