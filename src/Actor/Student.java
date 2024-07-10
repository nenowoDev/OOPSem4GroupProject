package Actor;

import java.io.*;
import java.util.*;

public class Student extends Person implements StudentBehavior {
    private ArrayList<Subject> registeredSubjects;
    private Subject subject = new Subject();

    public Student(String id, String name) {

        super(id, name);
        try {
            Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
            registeredSubjects = subject.readSubjects(inpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found!");
        }

    }

    // 1. Search subject
    public void searchSubject(Scanner sc) {
        boolean searchFound = false;
        int searchNo;
        do {
            System.out.println("Search Subject by?... ");
            System.out.println("1. Subject Name");
            System.out.println("2. Subject Code");
            System.out.println("3. Subject's Credit Hour");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid option (1-3): ");
                sc.next(); // Consume the invalid input
            }

            searchNo = sc.nextInt();
            sc.nextLine(); // Consume the newline character
        } while (searchNo > 3 || searchNo < 1);

        System.out.println("\nYou selected option " + searchNo);
        switch (searchNo) {
            case 1:
                System.out.print("Enter Subject Name: ");
                String nameSearch = sc.nextLine().trim();
                System.out.println("\n-------------------------------------------------------------");
                System.out.printf("%-8s%-15s%-40s\n", "Code", "Credit Hour", "Name");
                System.out.println("-------------------------------------------------------------");

                for (Subject subject : registeredSubjects) {
                    if (subject.getName().equalsIgnoreCase(nameSearch)) {
                        System.out
                                .println(subject.getCode() + "     " + subject.getCreditHour() + "     "
                                        + subject.getName());
                        searchFound = true;
                    }
                }
                if (!searchFound) {
                    System.out.println("Subject with name \"" + nameSearch + "\" not found.");
                }
                break;
            case 2:
                System.out.print("Enter Subject Code: ");
                String codeSearch = sc.nextLine().trim();
                System.out.println("\n-------------------------------------------------------------");
                System.out.printf("%-8s%-15s%-40s\n", "Code", "Credit Hour", "Name");
                System.out.println("-------------------------------------------------------------");

                for (Subject subject : registeredSubjects) {
                    if (subject.getCode().equalsIgnoreCase(codeSearch)) {
                        System.out
                                .println(subject.getCode() + "     " + subject.getCreditHour() + "     "
                                        + subject.getName());
                        searchFound = true;
                    }
                }
                if (!searchFound) {
                    System.out.println("Subject with code \"" + codeSearch + "\" not found.");
                }
                break;
            case 3:
                System.out.print("Enter Subject's Credit Hour: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Please enter a valid credit hour (integer): ");
                    sc.next(); // Consume the invalid input
                }
                int creditHourSearch = sc.nextInt();
                System.out.println("\n-------------------------------------------------------------");
                System.out.printf("%-8s%-15s%-40s\n", "Code", "Credit Hour", "Name");
                System.out.println("-------------------------------------------------------------");

                sc.nextLine(); // Consume the newline character
                for (Subject subject : registeredSubjects) {
                    if (subject.getCreditHour() == creditHourSearch) {
                        System.out
                                .println(subject.getCode() + "     " + subject.getCreditHour() + "     "
                                        + subject.getName());
                        searchFound = true;
                    }
                }
                if (!searchFound) {
                    System.out.println("No subjects found with credit hour \"" + creditHourSearch + "\".");
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

        boolean alreadyRegistered = false;

        // Check if subject is already registered by the student
        try (BufferedReader br = new BufferedReader(new FileReader("src/studentTakeSubject.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(", ");
                if (details[0].equals(studentID) && details[1].equalsIgnoreCase(code)) {
                    alreadyRegistered = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // If subject is already registered, inform the student and return
        if (alreadyRegistered) {
            System.out.println("Failed to register: Subject already registered.");
            return;
        }

        // Calculate total credit hours the student is already registered for
        int totalCreditHours = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/studentTakeSubject.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(", ");
                if (details[0].equals(studentID)) {
                    for (Subject subject : registeredSubjects) {
                        if (subject.getCode().equalsIgnoreCase(details[1])) {
                            totalCreditHours += subject.getCreditHour();
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if subject is open for registration and student's credit hours limit
        boolean subjectFound = false;
        for (Subject subject : registeredSubjects) {
            if (subject.getCode().equalsIgnoreCase(code)) {
                subjectFound = true;
                if (subject.getFlag()) { // Check if subject is open FOR REGISTRATION
                    if (totalCreditHours + subject.getCreditHour() <= 18) {
                        // Add subject registration to studentRegisterSubject file
                        try (FileWriter writer = new FileWriter("src/studentRegisterSubject.csv", true)) {
                            writer.append(studentID).append(", ").append(code).append("\n");
                            System.out.println("Subject registration request sent successfully: " + code);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Error writing to file.");
                        }
                    } else {
                        System.out.println("Failed to register: Your total credit hours exceed 18.");
                    }
                } else {
                    System.out.println("Failed to register: Subject is closed for registration.");
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

        // Read studentSubject file and search to check Whether the subject isregistered

        try (BufferedReader br = new BufferedReader(new FileReader("src/studentTakeSubject.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(studentID)) {
                    if (parts[1].equalsIgnoreCase(code)) {
                        subjectFound = true;
                        // RETRIEVE data from registeredSubjects list
                        String subjectName = registeredSubjects.stream()
                                .filter(subject -> subject.getCode().equalsIgnoreCase(code))
                                .map(Subject::getName)
                                .findFirst()
                                .orElse("Unknown Subject");

                        // WRITE to DroppingSubject
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
        System.out.println("\nCourse Available:");
        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-13s%-15s%-40s\n", "  Code", "Credit Hour", "Name");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < registeredSubjects.size(); i++) {
            System.out.println(
                    "  " + registeredSubjects.get(i).getCode() + "        " + registeredSubjects.get(i).getCreditHour()
                            + "         " + registeredSubjects.get(i).getName());
        }
        System.out.println("-------------------------------------------------------------\n");
    }

    // 5. List of Student's Subjects
    public void listStudentSubjects(String studentID) {
        System.out.println("\nYour current course enrollments ");
        System.out.println("\n-------------------------------------------------------------");
        System.out.printf("%-8s%-15s%-40s\n", "Code", "Credit Hour", "Name");
        System.out.println("-------------------------------------------------------------");
        try {
            Scanner inpFile = new Scanner(new File("src/studentTakeSubject.csv"));
            boolean studentFound = false;

            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(", ");
                if (parts[0].equals(studentID)) {
                    studentFound = true;
                    for (int i = 1; i < parts.length; i++) {
                        boolean subjectFound = false;
                        for (Subject subject : registeredSubjects) {
                            if (subject.getCode().equalsIgnoreCase(parts[i])) {
                                System.out.println(
                                        subject.getCode() + "    " + subject.getCreditHour() + "    "
                                                + subject.getName());
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
            System.out.println("File not found: src/studentTakeSubject.csv");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
