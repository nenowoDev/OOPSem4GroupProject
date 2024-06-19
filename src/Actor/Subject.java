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
    private boolean flag;
    //private ArrayList<Subject> subjectList;
    // private ArrayList<Student>registeredStudents;
    // private Lecturer lecturer;
    // boolean confirm;

    // Constructor, getters, setters, etc.

    public Subject(String code, String name, boolean flag, int creditHour) {
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

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag=flag;
    }

    public void readFile() throws IOException {
        ArrayList<Subject> subjectList = new ArrayList<>();
        Scanner inpFile = new Scanner(new File("subjectList.csv"));
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String code = inpFile.next();
            int creditHour = inpFile.nextInt();
            boolean flag = inpFile.nextBoolean();
            String name = inpFile.nextLine();
            name = name.substring(1);

            Subject subject = new Subject(code, name, flag, creditHour);
            subjectList.add(subject);
        }
        inpFile.close();
    }

    public void display() {
        //
        ArrayList<Subject> subjectList = new ArrayList<>();
        //
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
