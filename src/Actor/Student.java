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

    }

    public Student() {
        registeredSubjects = new ArrayList<>();

        LoadSubject();
    }

    public void LoadSubject() {
        System.out.println("Loading subjects...");
<<<<<<< Updated upstream
        String filePath = "src/subjectList.csv";
=======
        String filePath = "src\\subjectList.csv";
>>>>>>> Stashed changes

        try {
            Scanner readfile = new Scanner(new File(filePath));
            while (readfile.hasNextLine()) {
                String line = readfile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    code = parts[0].trim();
                    creditHour = Integer.parseInt(parts[1].trim());
                    flag = Boolean.parseBoolean(parts[2].trim());
                    name = parts[3].trim();
                    registeredSubjects.add(new Subject(code, name, flag, creditHour));
                    System.out.println("Loaded subject: " + code + " " + name + " " + creditHour + " " + flag);
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
            readfile.close();
        } catch (FileNotFoundException e) {
            System.err.println("subjectList.csv not found!");
        }

    }

    // 1. Search subject
    public void searchSubject() {
        Scanner sc = new Scanner(System.in);
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
        } while (searchNo > 3);
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

        sc.close();

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