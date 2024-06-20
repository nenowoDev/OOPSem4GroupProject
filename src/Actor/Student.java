package Actor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// public class Student extends Person {
//     List<Course> registeredCourses;
//     public Course findSubject(String code){return new Course();}
//     public void registerSubject(Course course){}
//     public void dropSubject(Course course){}
//     public List<Course> displayListOfSubjects(){return new ArrayList<>();}
// }

public class Student extends Person {
    private ArrayList<Subject> registeredSubjects;
    private String name, code;
    private int creditHour;
    private boolean flag;

    public Student(String id, String name) {

        super(id, name);
        registeredSubjects = new ArrayList<>();
        try {
            Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
            registeredSubjects = readSubjects(inpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }

    }

    public Student() {
        registeredSubjects = new ArrayList<>();
        try {
            Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
            registeredSubjects = readSubjects(inpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }

    }

    public ArrayList<Subject> readSubjects(Scanner inpFile) {
        ArrayList<Subject> subjects = new ArrayList<>();
        while (inpFile.hasNextLine()) {
            String line = inpFile.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String code = parts[0];
                int creditHour = Integer.parseInt(parts[1]);
                boolean flag = Boolean.parseBoolean(parts[2]);
                String name = parts[3];
                subjects.add(new Subject(code, name, flag, creditHour));
            } else {
                System.out.println("Invalid data format: " + line);
            }
        }
        inpFile.close();
        return subjects;
    }

    // 1. Search subject
    public void searchSubject(Scanner sc) {

        int searchNo;
        do {

            System.out.println("Search Subject by?... ");
            System.out.println("1.Subject Name");
            System.out.println("2.Subject Code");
            System.out.println("3.Subject's Credit Hour");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid option (1-3): ");
                sc.next(); // Consume the invalid input
            }

            searchNo = sc.nextInt();
            sc.nextLine();
        } while (searchNo > 3 || searchNo < 1);
        System.out.println("you click " + searchNo);
        switch (searchNo) {
            case 1:
                System.out.print("Enter Subject Name: ");
                String nameSearch = sc.nextLine().trim();
                for (Subject subject : registeredSubjects) {
                    if (subject.getName().equalsIgnoreCase(nameSearch)) {
                        System.out
                                .println(subject.getCode() + "  " + subject.getCreditHour() + "  " + subject.getName());
                    }
                }
                break;
            case 2:
                System.out.print("Enter Subject Code: ");
                String codeSearch = sc.nextLine().trim();
                for (Subject subject : registeredSubjects) {
                    if (subject.getCode().equalsIgnoreCase(codeSearch)) {
                        System.out
                                .println(subject.getCode() + "  " + subject.getCreditHour() + "  " + subject.getName());
                    }
                }
                break;
            case 3:
                System.out.print("Enter Subject's Credit Hour: ");
                int creditHourSearch = sc.nextInt();
                for (Subject subject : registeredSubjects) {
                    if (subject.getCreditHour() == creditHourSearch) {
                        System.out
                                .println(subject.getCode() + "  " + subject.getCreditHour() + "  " + subject.getName());
                    }
                }
                break;
            default:
                System.out.println("Invalid option.");
        }

    }

    // 2. Register Subject
    public void registerSubject() {
        System.out.println("Register Subject");
    }

    // 3. Drop Subject
    public void dropSubject() {
        System.out.println("dROP SUBJET");
    }

    // 4. List of Subjects
    public void listSubjects() {
        System.out.println("Registered Subjects:");
        System.out.println("--------------------");
        for (int i = 0; i < registeredSubjects.size(); i++) {
            System.out.println(registeredSubjects.get(i).getCode() + "  " + registeredSubjects.get(i).getCreditHour()
                    + "  " + registeredSubjects.get(i).getName());
        }
    }

}
