package Actor;

import java.io.*;
import java.util.*;

/**
 * Represents a Lecturer, extending the Person class.
 * Provides functionalities for viewing, choosing, dropping subjects, 
 * and listing students registered for subjects.
 */
public class Lecturer extends Person {
    private ArrayList<Subject> subjectsToTeach;
    private static final String SUBJECT_LIST_PATH = "src/subjectList.csv";
    private static final String LECTURER_TAKE_SUBJECT_PATH = "src/lecturerTakeSubjectToTeach.csv";
    private static final String STUDENT_LIST_PATH = "src/studentList.csv";
    private static final String STUDENT_SUBJECT_PATH = "src/studentSubject.csv";
    private static final String LECTURER_LIST_PATH = "src/lecturerList.csv";

    /**
     * Initializes a new Lecturer with the given ID and name.
     * @param id Lecturer ID
     * @param name Lecturer name
     */
    public Lecturer(String id, String name) {
        super(id, name);
        subjectsToTeach = new ArrayList<>();
        loadSubjectsToTeach();
    }

    /**
     * Default constructor.
     */
    public Lecturer() {
        subjectsToTeach = new ArrayList<>();
    }

    /**
     * Loads the subjects that the lecturer has chosen to teach from the CSV file.
     */
    private void loadSubjectsToTeach() {
        try (Scanner inpFile = new Scanner(new File(LECTURER_TAKE_SUBJECT_PATH))) {
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(super.getId())) {
                    String code = parts[1];
                    String name = parts[2];
                    subjectsToTeach.add(new Subject(code, name, false, 0));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + LECTURER_TAKE_SUBJECT_PATH);
        }
    }

    /**
     * Displays the details of all available subjects from the CSV file.
     */
    public void viewSubjectDetails() {
        System.out.println("Subject Details:");
        System.out.println("----------------");
        try (Scanner inpFile = new Scanner(new File(SUBJECT_LIST_PATH))) {
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String code = parts[0];
                    int creditHour = Integer.parseInt(parts[1]);
                    boolean flag = Boolean.parseBoolean(parts[2]);
                    String name = parts[3];
                    System.out.println("Code: " + code + ", Credit Hour: " + creditHour + ", Name: " + name + ", Flag: " + flag);
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + SUBJECT_LIST_PATH);
        }
        System.out.println("Press 0 to return to menu.");
        try (Scanner scanner = new Scanner(System.in)) {
            while (!scanner.hasNextInt() || scanner.nextInt() != 0) {
                System.out.println("Invalid input. Please press 0 to return to menu.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    /**
     * Allows the lecturer to choose a subject to teach.
     * The available subjects are displayed, and the lecturer can select one by entering its number.
     * The chosen subject is recorded in the CSV file.
     */
    public void chooseSubjectToTeach() {
        System.out.println("Choose Subject to Teach:");
        System.out.println("------------------------");

        // Display all available subjects
        System.out.println("Available Subjects:");
        List<String[]> subjects = new ArrayList<>();
        try (Scanner inpFile = new Scanner(new File(SUBJECT_LIST_PATH))) {
            int index = 1;
            while (inpFile.hasNextLine()) {
                String line = inpFile.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    subjects.add(parts);
                    String code = parts[0];
                    String name = parts[3];
                    System.out.println(index + ". Code: " + code + ", Name: " + name);
                    index++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found: " + SUBJECT_LIST_PATH);
        }

        // Prompt lecturer to enter subject code to choose
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the subject you want to teach: ");
        int choice;
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.next(); // Consume the invalid input
        }
        choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (choice <= 0 || choice > subjects.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String[] selectedSubject = subjects.get(choice - 1);
        String code = selectedSubject[0];
        String name = selectedSubject[3];

        // Check if the subject is already being taught by this lecturer
        for (Subject subject : subjectsToTeach) {
            if (subject.getCode().equalsIgnoreCase(code)) {
                System.out.println("You are already teaching this subject: " + code + " - " + name);
                System.out.println("Press 0 to return to menu.");
                while (!sc.hasNextInt() || sc.nextInt() != 0) {
                    System.out.println("Invalid input. Please press 0 to return to menu.");
                    sc.nextLine(); // Consume the invalid input
                }
                return;
            }
        }

        // Write to lecturerTakeSubjectToTeach.csv if the subject is not already being taught
        try (FileWriter writer = new FileWriter(LECTURER_TAKE_SUBJECT_PATH, true)) {
            writer.append(super.getId()).append(",")
                  .append(code).append(",").append(name).append("\n");
            System.out.println("Subject chosen successfully: " + code + " - " + name);
            // Add the subject to the local list
            subjectsToTeach.add(new Subject(code, name, false, Integer.parseInt(selectedSubject[1])));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing to file.");
        }

        System.out.println("Press 0 to return to menu.");
        while (!sc.hasNextInt() || sc.nextInt() != 0) {
            System.out.println("Invalid input. Please press 0 to return to menu.");
            sc.nextLine(); // Consume the invalid input
        }
    }

    /**
     * Allows the lecturer to drop a subject they are teaching.
     * The subjects currently taught by the lecturer are displayed, and the lecturer can select one to drop by entering its number.
     * The subject is removed from the CSV file.
     */
    public void dropSubject() {
        System.out.println("Drop Subject:");
        System.out.println("-------------");

        // Display all subjects currently taught by the lecturer
        listLecturerSubjects();

        // Prompt lecturer to enter subject number to drop
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of the subject you want to drop: ");
        int choice;
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.next(); // Consume the invalid input
        }
        choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (choice <= 0 || choice > subjectsToTeach.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String code = subjectsToTeach.get(choice - 1).getCode();
        boolean subjectFound = false;

        File tempFile = new File("src/tempLecturerSubject.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(LECTURER_TAKE_SUBJECT_PATH));
             FileWriter writer = new FileWriter(tempFile)) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(super.getId()) && parts[1].equalsIgnoreCase(code)) {
                    subjectFound = true;
                    System.out.println("Dropping subject: " + code);
                } else {
                    writer.write(line + "\n");
                }
            }

            if (!subjectFound) {
                System.out.println("Subject not found in your subjects to teach.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error processing file.");
        }

        // Replace old file with the new file
        if (tempFile.renameTo(new File(LECTURER_TAKE_SUBJECT_PATH))) {
            // Remove the subject from the local list
            subjectsToTeach.removeIf(subject -> subject.getCode().equalsIgnoreCase(code));
        } else {
            System.out.println("Error replacing the file.");
        }

        System.out.println("Press 0 to return to menu.");
        while (!sc.hasNextInt() || sc.nextInt() != 0) {
            System.out.println("Invalid input. Please press 0 to return to menu.");
            sc.            nextLine(); // Consume the invalid input
        }
    }

    /**
     * Displays the list of students registered for a specific subject taught by the lecturer.
     * The subjects currently taught by the lecturer are displayed, and the lecturer can select one to view the registered students by entering its number.
     */
    public void subjectStudentList() {
        listLecturerSubjects(); // Display all subjects currently taught by the lecturer
        System.out.print("Enter number of subject for which you want to show registered students: ");
        Scanner sc = new Scanner(System.in);
        int number;
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.next(); // Consume the invalid input
        }
        number = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (number <= 0 || number > subjectsToTeach.size()) {
            System.out.println("Invalid Entry");
        } else {
            displayStudentCount(subjectsToTeach.get(number - 1).getCode()); // Display students registered for the selected subject
        }

        System.out.println("Press 0 to return to menu.");
        while (!sc.hasNextInt() || sc.nextInt() != 0) {
            System.out.println("Invalid input. Please press 0 to return to menu.");
            sc.nextLine(); // Consume the invalid input
        }
    }

    /**
     * Displays the subjects currently taught by the lecturer.
     */
    private void listLecturerSubjects() {
        System.out.println("Subjects you are teaching:");
        for (int i = 0; i < subjectsToTeach.size(); i++) {
            Subject subject = subjectsToTeach.get(i);
            System.out.println((i + 1) + ". " + subject.getCode() + " - " + subject.getName());
        }
    }

    /**
     * Displays the list of students registered for a given subject.
     * Reads student registrations from the CSV file and maps student IDs to names using getStudentMap().
     * @param subjectCode The code of the subject
     */
    private void displayStudentCount(String subjectCode) {
        System.out.println("Students registered for subject " + subjectCode + ":");
        Map<String, String> studentMap = getStudentMap();
        List<String> registeredStudents = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(STUDENT_SUBJECT_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equalsIgnoreCase(subjectCode)) {
                    registeredStudents.add(parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
        }

        System.out.println("Total number of students: " + registeredStudents.size());
        for (String studentId : registeredStudents) {
            String studentName = studentMap.get(studentId);
            if (studentName != null) {
                System.out.println("Student ID: " + studentId + ", Name: " + studentName);
            } else {
                System.out.println("Student ID: " + studentId + ", Name: Not found in student list");
            }
        }
    }

    /**
     * Reads the student list from the CSV file and creates a map of student IDs to student names.
     * @return A map where the keys are student IDs and the values are student names.
     */
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
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
        }
        return studentMap;
    }

    /**
     * Main method for testing the functionalities of the Lecturer class.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // Load lecturers from lecturerList.csv
        Map<String, String> lecturerMap = new HashMap<>();
        try (Scanner inpFile = new Scanner(new File(LECTURER_LIST_PATH))) {
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
            System.out.println("File not found: " + LECTURER_LIST_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading from file.");
        }

        // Create lecturers from the map
        for (Map.Entry<String, String> entry : lecturerMap.entrySet()) {
            Lecturer lecturer = new Lecturer(entry.getKey(), entry.getValue());

            // Lecturer operations
            System.out.println("Lecturer: " + lecturer.getName());
            lecturer.chooseSubjectToTeach();
            lecturer.dropSubject();
            lecturer.subjectStudentList();
            lecturer.viewSubjectDetails();
        }
    }
}

