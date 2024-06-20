package Actor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private Subject subject = new Subject();
    private String name, code;
    private int creditHour;
    private boolean flag;

    public Student(String id, String name) {

        super(id, name);
        try {
            Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
            registeredSubjects = subject.readSubjects(inpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }

        // try {
        // Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
        // registeredSubjects = readSubjects(inpFile);
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // System.out.println("File not found!");
        // } catch (Exception e) {
        // e.printStackTrace();
        // System.out.println("Error");
        // }

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
                    } else {
                        System.out.println("Subject not found");
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
                    } else {
                        System.out.println("Subject not found");
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
                    } else {
                        System.out.println("Subject not found");
                    }
                }
                break;
            default:
                System.out.println("Invalid option.");
        }

    }

    // 2. Register Subject
    public void registerSubject(String studentID) {
        System.out.println("Register Subject");
        System.out.println("Enter subject code to register: ");
        Scanner sc = new Scanner(System.in);
        String code = sc.nextLine().trim();
        boolean subjectFound = false;

        for (Subject subject : registeredSubjects) {
            if (subject.getCode().equalsIgnoreCase(code)) {
                subjectFound = true;
                try (FileWriter writer = new FileWriter("src/Registering.csv", true)) {
                    writer.append(studentID).append(",")
                            .append("FALSE").append(",")
                            .append(code).append(",").append(subject.getName()).append("\n");
                    System.out.println("Subject registered successfully: " + code);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error writing to file.");
                }
                break;
            }
        }

        if (!subjectFound) {
            System.out.println("Subject not found.");
        }

    }

    // 3. Drop Subject
    public void dropSubject(String studentID) {
        System.out.println("Drop Subject");
        System.out.println("Enter subject code to drop: ");
        Scanner sc = new Scanner(System.in);
        String code = sc.nextLine().trim();

        boolean subjectFound = false;

        // Read studentSubject.csv to check Whether the subject is registered
        try (BufferedReader br = new BufferedReader(new FileReader("src/studentSubject.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(studentID)) {
                    for (int i = 2; i < parts.length; i++) {
                        if (parts[i].equalsIgnoreCase(code)) {
                            subjectFound = true;
                            // Get subject name from registeredSubjects list
                            String subjectName = registeredSubjects.stream()
                                    .filter(subject -> subject.getCode().equalsIgnoreCase(code))
                                    .map(Subject::getName)
                                    .findFirst()
                                    .orElse("Unknown Subject");

                            // Write to DroppingSubject.csv
                            try (FileWriter writer = new FileWriter("src/DroppingSubject.csv", true)) {
                                writer.append(studentID).append(",")
                                        .append("FALSE").append(",")
                                        .append(code).append(",").append(subjectName).append("\n");
                                System.out.println("Subject drop requested: " + code);
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Error writing to file.");
                            }
                            break;
                        }
                    }
                }
                if (subjectFound)
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
        }

        if (!subjectFound) {
            System.out.println("Subject not found in your registered subjects.");
        }
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

    // just planning to add this one, if i have time
    // 5. List of Student's Subjects
    public void listStudentSubjects(String studentID) {
        System.out.println("Display your subjects:");
        try {
            Scanner inpFile = new Scanner(new File("src/studentSubject.csv"));
            boolean studentFound = false;

            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(studentID)) {
                    studentFound = true;
                    for (int i = 2; i < parts.length; i++) {
                        boolean subjectFound = false;
                        for (Subject subject : registeredSubjects) {
                            if (subject.getCode().equalsIgnoreCase(parts[i])) {
                                System.out.println(
                                        subject.getCode() + "  " + subject.getCreditHour() + "  " + subject.getName());
                                subjectFound = true;
                                break;
                            }
                        }
                        if (!subjectFound) {
                            System.out.println("Subject not found: " + parts[i]);
                        }
                    }
                }
            }

            if (!studentFound) {
                System.out.println("Student ID not found: " + studentID);
            }

            inpFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: src/studentSubject.csv");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
