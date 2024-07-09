package Actor;

import java.io.*;
import java.util.*;

public class Lecturer extends Person implements LecturerBehavior {
    private ArrayList<Subject> subjectsToTeach;
    private static final String SUBJECT_LIST_PATH = "src/subjectList.csv";
    private static final String LECTURER_TAKE_SUBJECT_PATH = "src/lecturerTakeSubjectToTeach.csv";
    private static final String STUDENT_LIST_PATH = "src/studentList.csv";
    private static final String STUDENT_SUBJECT_PATH = "src/studentTakeSubject.csv";

    public Lecturer(String id, String name) {
        super(id, name);
        subjectsToTeach = new ArrayList<>();
        loadSubjectsToTeach();
    }

    public Lecturer() {
        subjectsToTeach = new ArrayList<>();
    }

    private void loadSubjectsToTeach() {
        try (Scanner inpFile = new Scanner(new File(LECTURER_TAKE_SUBJECT_PATH))) {
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].trim().equals(super.getId())) {
                    String code = parts[1].trim();
                    String name = parts[2].trim();
                    subjectsToTeach.add(new Subject(code, name, false, 0));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + LECTURER_TAKE_SUBJECT_PATH);
        }
    }


    //1. Lecturer can view all subject offered
    public void viewSubjectDetails() {
        // String title = "Subject Details:";
        // int totalWidth = 10 + 15 + 40; // Adjusted width for better alignment
        // int padding = (totalWidth - title.length()) / 2;
        // String paddedTitle = String.format("%" + (padding + title.length()) + "s", title);

        System.out.println("-------------------------------------------------------------");
        System.out.println("                   LIST OF SUBJECTS");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-10s%-15s%-40s\n", "Code", "Credit Hour", "Name");
        System.out.println("-------------------------------------------------------------");

        Set<String> displayedSubjects = new HashSet<>();
        try (Scanner inpFile = new Scanner(new File(SUBJECT_LIST_PATH))) {
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String code = parts[0].trim();
                    int creditHour = Integer.parseInt(parts[1].trim());
                    String name = parts[3].trim();
                    if (!displayedSubjects.contains(code)) {
                        displayedSubjects.add(code);
                        System.out.printf("%-15s%-10s%-40s\n", code, creditHour, name);
                    }
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + SUBJECT_LIST_PATH);
        }
        System.out.println("-------------------------------------------------------------");
    //     System.out.print("Press 0 to return to menu: ");
    //     Scanner scanner = new Scanner(System.in);
    //     while (!scanner.hasNextInt() || scanner.nextInt() != 0) {
    //         System.out.print("Invalid input. Please press 0 to return to menu: ");
    //         scanner.nextLine();
    //     }
    }


    //2. Lecturer can choose subject they want to teach
    public void chooseSubjectToTeach() {
        // String title = "Choose Subject to Teach";
        // int totalWidth = 5 + 10 + 30; // Widths of the columns plus the "No." column
        // int padding = (totalWidth - title.length()) / 2;
        // String paddedTitle = String.format("%" + (padding + title.length()) + "s", title);

        System.out.println("-------------------------------------------------------");
        System.out.println("               Choose Subject to Teach");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-5s%-10s%-30s\n", "No.", "Code", "Name");
        System.out.println("-------------------------------------------------------");

        List<String> subjectList = new ArrayList<>();
        try (Scanner inpFile = new Scanner(new File(SUBJECT_LIST_PATH))) {
            int index = 1;
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String code = parts[0].trim();
                    String name = parts[3].trim();
                    subjectList.add(code + "," + name);
                    System.out.printf("%-5d%-10s%-30s\n", index, code, name);
                    index++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + SUBJECT_LIST_PATH);
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------------------------------------\n");
        System.out.print("Enter the number of the subject you want to teach: ");
        int choice;
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            sc.next();
        }
        choice = sc.nextInt();
        sc.nextLine();

        if (choice > 0 && choice <= subjectList.size()) {
            String[] selectedSubject = subjectList.get(choice - 1).split(",");
            String code = selectedSubject[0].trim();
            String name = selectedSubject[1].trim();

            boolean isSubjectAlreadyAdded = false;
            boolean isCurrentLecturerTeaching = false;
            try (Scanner checkFile = new Scanner(new File(LECTURER_TAKE_SUBJECT_PATH))) {
                while (checkFile.hasNextLine()) {
                    String checkLine = checkFile.nextLine();
                    String[] checkParts = checkLine.split(",");
                    if (checkParts.length == 3 && checkParts[1].trim().equals(code)) {
                        isSubjectAlreadyAdded = true;
                        if (checkParts[0].trim().equals(super.getId())) {
                            isCurrentLecturerTeaching = true;
                        }
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File not found: " + LECTURER_TAKE_SUBJECT_PATH);
            }

            if (isCurrentLecturerTeaching) {
                System.out.println("You are already teaching this subject.");
                // System.out.print("Press 0 to return to menu: ");
                // while (!sc.hasNextInt() || sc.nextInt() != 0) {
                //     System.out.print("Invalid input. Please press 0 to return to menu: ");
                //     sc.nextLine();
                // }
                return;
            } else if (isSubjectAlreadyAdded) {
                System.out.println("There is another lecturer teaching this subject already.");
                // System.out.print("Press 0 to return to menu: ");
                // while (!sc.hasNextInt() || sc.nextInt() != 0) {
                //     System.out.print("Invalid input. Please press 0 to return to menu: ");
                //     sc.nextLine();
                // }
                return;
            }

            try (FileWriter writer = new FileWriter(LECTURER_TAKE_SUBJECT_PATH, true)) {
                writer.append(super.getId()).append(",")
                      .append(code).append(",").append(name).append("\n");
                System.out.println("Subject chosen successfully: " + code + " - " + name);
                subjectsToTeach.add(new Subject(code, name, false, 0));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error writing to file.");
            }
        } else {
            System.out.println("Invalid choice. Subject not found.");
        }
        System.out.println();
        System.out.println("-------------------------------------------------------");
        // System.out.print("Press 0 to return to menu: ");
        // while (!sc.hasNextInt() || sc.nextInt() != 0) {
        //     System.out.print("Invalid input. Please press 0 to return to menu: ");
        //     sc.nextLine();
        // }
    }


    //3. Lecturer can drop subject if they don't want to teach
    public void dropSubject() {
        if (subjectsToTeach.isEmpty()) {
            System.out.println("You are not teaching any subjects.");
            System.out.print("Press 0 to return to menu: ");
            Scanner sc = new Scanner(System.in);
            while (!sc.hasNextInt() || sc.nextInt() != 0) {
                System.out.print("Invalid input. Please press 0 to return to menu: ");
                sc.nextLine();
            }
            return;
        }

        String separator = "-------------------------------------------------------";
        String title = "Drop Subject:";
        int paddingSize = (separator.length() - title.length()) / 2;
        String paddedTitle = " ".repeat(paddingSize) + title;

        System.out.println(separator);
        System.out.println(paddedTitle);
        System.out.println(separator);

        // List subjects currently taught by the lecturer
        System.out.printf("%-5s%-10s%-30s\n", "No.", "Code", "Name");
        System.out.println("-------------------------------------------------------");
        for (int i = 0; i < subjectsToTeach.size(); i++) {
            Subject subject = subjectsToTeach.get(i);
            System.out.printf("%-5d%-10s%-30s\n", (i + 1), subject.getCode(), subject.getName());
        }

        Scanner sc = new Scanner(System.in);
        System.out.println(separator);
        System.out.print("\nEnter the number of the subject you want to drop or Press 0 to return to menu: ");
        int choice;
        while (true) {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice == 0) {
                    return;
                } else if (choice > 0 && choice <= subjectsToTeach.size()) {
                    break;
                } else {
                    System.out.print("Invalid choice. Enter the number of the subject you want to drop or Press 0 to return to menu: ");
                }
            } else {
                System.out.print("Invalid input. Please enter a valid number or Press 0 to return to menu: ");
                sc.next();
            }
        }

        String code = subjectsToTeach.get(choice - 1).getCode();
        boolean subjectFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader(LECTURER_TAKE_SUBJECT_PATH));
             PrintWriter writer = new PrintWriter(new FileWriter("src/tempLecturerTakeSubjectToTeach.csv"))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].trim().equals(super.getId()) && parts[1].trim().equalsIgnoreCase(code)) {
                    subjectFound = true;
                    System.out.println("Subject dropped: " + code);
                } else {
                    writer.println(line);
                }
            }

            if (!subjectFound) {
                System.out.println("Subject not found in your subjects to teach.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing file.");
        }

        File originalFile = new File(LECTURER_TAKE_SUBJECT_PATH);
        File tempFile = new File("src/tempLecturerTakeSubjectToTeach.csv");

        if (originalFile.delete()) {
            tempFile.renameTo(originalFile);
            subjectsToTeach.removeIf(subject -> subject.getCode().equalsIgnoreCase(code));
        } else {
            System.out.println("Error replacing the file.");
        }
        System.out.println();
        System.out.println(separator);
        // System.out.print("Press 0 to return to menu: ");
        // while (!sc.hasNextInt() || sc.nextInt() != 0) {
        //     System.out.print("Invalid input. Please press 0 to return to menu: ");
        //     sc.nextLine();
        // }
    }


    //4. Lecturer can view the list of student register for the subject they choose to teach
    public void subjectStudentList() {
        if (subjectsToTeach.isEmpty()) {
            System.out.println("You are not teaching any subjects.");
            System.out.print("Press 0 to return to menu: ");
            Scanner sc = new Scanner(System.in);
            while (!sc.hasNextInt() || sc.nextInt() != 0) {
                System.out.print("Invalid input. Please press 0 to return to menu: ");
                sc.nextLine();
            }
            return;
        }

        System.out.println("-------------------------------------------------------");
        System.out.println("           Students Registered for Subject");
        System.out.println("-------------------------------------------------------");

        System.out.printf("%-5s%-10s%-30s\n", "No.", "Code", "Name");
        System.out.println("-------------------------------------------------------");
        for (int i = 0; i < subjectsToTeach.size(); i++) {
            Subject subject = subjectsToTeach.get(i);
            System.out.printf("%-5d%-10s%-30s\n", (i + 1), subject.getCode(), subject.getName());
        }
        System.out.println("-------------------------------------------------------\n");
        System.out.print("Enter number of subject for which you want to show registered students: ");
        Scanner sc = new Scanner(System.in);
        int number;
        while (!sc.hasNextInt()) {
            System.out.print("\nInvalid input. Please enter a valid number: ");
            sc.next();
        }
        number = sc.nextInt();
        sc.nextLine();

        if (number <= 0 || number > subjectsToTeach.size()) {
            System.out.println("Invalid Entry");
        } else {
            displayStudentCount(subjectsToTeach.get(number - 1).getCode());
        }

        // System.out.print("Press 0 to return to menu: ");
        // while (!sc.hasNextInt() || sc.nextInt() != 0) {
        //     System.out.print("Invalid input. Please press 0 to return to menu: ");
        //     sc.nextLine();
        // }
    }

    
    private void displayStudentCount(String subjectCode) {
        // String title = "Students registered for subject " + subjectCode + ":";
        // int totalWidth = 15 + 30; // Widths of the columns
        // int padding = (totalWidth - title.length()) / 2;
        // String paddedTitle = String.format("%" + (padding + title.length()) + "s", title);

        // System.out.println("-------------------------------------------------------");
        // System.out.println(paddedTitle);
        // System.out.println("-------------------------------------------------------");

        Map<String, String> studentMap = getStudentMap();
        List<String> registeredStudents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_SUBJECT_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].trim().equalsIgnoreCase(subjectCode)) {
                    registeredStudents.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
        }
        System.out.println("\nStudents registered for subject " + subjectCode + ": " + registeredStudents.size() + " students");
        // System.out.println("Total number of students: " + registeredStudents.size());
        System.out.println("\n-------------------------------------------------------");
        System.out.printf("%-15s%-30s\n", "Student ID", "Name");
        System.out.println("-------------------------------------------------------");
        for (String studentId : registeredStudents) {
            String studentName = studentMap.get(studentId);
            if (studentName != null) {
                System.out.printf("%-15s%-30s\n", studentId, studentName);
            } else {
                System.out.printf("%-15s%-30s\n", studentId, "Not found in student list");
            }
        }
        System.out.println("-------------------------------------------------------");
    }


    private Map<String, String> getStudentMap() {
        Map<String, String> studentMap = new HashMap<>();
        try (Scanner inpFile = new Scanner(new File(STUDENT_LIST_PATH))) {
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String studentId = parts[0].trim();
                    String studentName = parts[1].trim();
                    studentMap.put(studentId, studentName);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + STUDENT_LIST_PATH);
        }
        return studentMap;
    }

    public static void main(String[] args) {
        Map<String, String> lecturerMap = new HashMap<>();
        try (Scanner inpFile = new Scanner(new File("src/lecturerList.csv"))) {
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String lecturerId = parts[0].trim();
                    String lecturerName = parts[1].trim();
                    lecturerMap.put(lecturerId, lecturerName);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: src/lecturerList.csv");
        }

        for (Map.Entry<String, String> entry : lecturerMap.entrySet()) {
            Lecturer lecturer = new Lecturer(entry.getKey(), entry.getValue());

            System.out.println("Lecturer: " + lecturer.getName());
            lecturer.chooseSubjectToTeach();
            lecturer.dropSubject();
            lecturer.subjectStudentList();
            lecturer.viewSubjectDetails();
        }
    }
}
