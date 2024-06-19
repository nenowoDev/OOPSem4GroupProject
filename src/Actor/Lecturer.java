package Actor;
import java.io.*;
import java.util.*;

/**
 * The Lecturer class extends the Person class and is responsible for
 * managing the subjects a lecturer can teach.
 * It provides functionality to view, choose, and drop subjects,
 * and list students registered for a specific subject.
 */
public class Lecturer extends Person {
    private ArrayList<Subject> subjectsToTeach; // List of subjects the lecturer is currently teaching
    private ArrayList<Subject> allSubjects; // List of all available subjects

    /**
     * Constructor to initialize a Lecturer with an ID and name.
     * @param id Lecturer's ID
     * @param name Lecturer's name
     */
    public Lecturer(String id, String name) {
        super(id, name);
        subjectsToTeach = new ArrayList<Subject>(); // Initialize the list of subjects the lecturer is teaching
        allSubjects = new ArrayList<>(); // Initialize the list of all available subjects
        try {
            getSubjectsFromFile(); // Load all subjects from the file
            getMySubjects(); // Load subjects the lecturer is currently teaching
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * View details of a specific subject by listing all subjects and
     * allowing the lecturer to select one for more details.
     */
    public void viewSubjectDetails() {
        listSubjects(); // Display all available subjects
        System.out.print("Enter number of subject to display details: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int number = scanner.nextInt(); // Read the user's input
            if (number <= 0 || number > allSubjects.size()) {
                System.out.println("Invalid number");
            } else {
                getALlStudentAndDisplay(allSubjects.get(number - 1).getCode()); // Display students registered for the selected subject
                getLecturer(allSubjects.get(number - 1).getCode()); // Display lecturer assigned to the selected subject
            }
        }
    }

    /**
     * Choose a subject to teach by listing all subjects and allowing
     * the lecturer to select one.
     */
    public void chooseSubjectToTeach() {
        listSubjects(); // Display all available subjects
        System.out.println("Enter subject number to teach");
        try (Scanner scanner = new Scanner(System.in)) {
            int number = scanner.nextInt(); // Read the user's input
            if (number <= 0 || number > allSubjects.size()) {
                System.out.println("Invalid number");
            } else {
                MakeRegistration(allSubjects.get(number - 1), this.id); // Register the lecturer to the selected subject
            }
        }
    }

    /**
     * Drop a subject that the lecturer is currently teaching by listing
     * the subjects and allowing the lecturer to select one to drop.
     */
    public void dropSubject() {
        System.out.println("Subjects which you are teaching");
        if (subjectsToTeach.size() <= 0) {
            System.out.println("You haven't registered any subject ");
            return;
        }

        System.out.println("Code\tCredit Hours\tName");
        System.out.println("--------------------------------------");
        int i = 1;
        for (Subject subject : subjectsToTeach) {
            System.out.println("No" + i + "  " + subject.getCode() + " \t" + subject.getCreditHour() + "\t" + subject.getName());
            i++;
        }

        System.out.println("Enter subject number to drop it");
        try (Scanner scanner = new Scanner(System.in)) {
            int number = scanner.nextInt(); // Read the user's input
            if (number < 1 || number > subjectsToTeach.size()) {
                System.out.println("Invalid number");
            } else {
                deleteSubject(subjectsToTeach.get(number - 1)); // Remove the selected subject from the lecturer's list
            }
        }
    }

    /**
     * Get the lecturer assigned to a specific subject by reading
     * the lecturerTakeSubjectToTeach.csv file.
     * @param code Subject code
     */
    private void getLecturer(String code) {
        System.out.println("Lecturer assigned to subject: " + code);
        try (BufferedReader reader = new BufferedReader(new FileReader("src/lecturerTakeSubjectToTeach.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String lecturerID = parts[0].trim();
                    String subjectCode = parts[1].trim();
                    if (subjectCode.equals(code)) {
                        found = true;
                        System.out.println("Lecturer ID who is teaching: " + lecturerID);
                    }
                }
            }
            if (!found) {
                System.out.println("No one is teaching this subject");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * List the students registered for a specific subject taught by the lecturer.
     */
    public void subjectStudentList() {
        listLecturerSubject(); // Display all subjects currently taught by the lecturer
        System.out.print("Enter number of subject for which you want to show registered students: ");
        try (Scanner scanner = new Scanner(System.in)) {
            int number = scanner.nextInt(); // Read the user's input
            if (number <= 0 || number > subjectsToTeach.size()) {
                System.out.println("Invalid Entry");
            } else {
                getALlStudentAndDisplay(subjectsToTeach.get(number - 1).getCode()); // Display students registered for the selected subject
            }
        }
    }

    /**
     * Get and display all students registered to a specific subject by reading
     * the studentTakeSubject.csv file.
     * @param code Subject code
     */
    private void getALlStudentAndDisplay(String code) {
        subjectsToTeach.clear();
        System.out.println("Students registered to subject: " + code);
        try (BufferedReader reader = new BufferedReader(new FileReader("src/studentTakeSubject.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String studentID = parts[0].trim();
                    String subjectCode = parts[1].trim();
                    if (subjectCode.equals(code)) {
                        found = true;
                        System.out.println("Student registration number: " + studentID);
                    }
                }
            }
            if (!found) {
                System.out.println("No student registered yet to this subject");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Register the lecturer to a subject and append the information
     * to the lecturerTakeSubjectToTeach.csv file.
     * @param subject Subject to register
     * @param id Lecturer's ID
     */
    private void MakeRegistration(Subject subject, String id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/lecturerTakeSubjectToTeach.csv", true))) {
            String line = id + "," + subject.getCode() + "," + subject.getCreditHour() + "," + subject.getName() + "\n";
            writer.append(line); // Append the registration information to the file
            System.out.println("Successfully registered");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the subjects the lecturer is currently teaching by reading
     * the lecturerTakeSubjectToTeach.csv file.
     * @throws IOException If an I/O error occurs
     */
    private void getMySubjects() throws IOException {
        subjectsToTeach.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/lecturerTakeSubjectToTeach.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String lecturerID = parts[0].trim();
                    String code = parts[1].trim();
                    int creditHours = Integer.parseInt(parts[2].trim());
                    String name = parts[3].trim();
                    if (lecturerID.equals(this.id)) {
                        subjectsToTeach.add(new Subject(code, name,false, creditHours)); // Add subject to the list if the lecturer ID matches
                    }
                }
            }
        }
    }

    /**
     * List the subjects currently taught by the lecturer.
     */
    public void listLecturerSubject() {
        System.out.println("Subjects which you are teaching");
        if (subjectsToTeach.size() <= 0) {
            System.out.println("You haven't registered any subject");
            return;
        }

        System.out.println("Code\tCredit Hours\tName");
        System.out.println("--------------------------------------");
        int i = 1;
        for (Subject subject : subjectsToTeach) {
            System.out.println("No" + i + "  " + subject.getCode() + " \t" + subject.getCreditHour() + "\t" + subject.getName());
            i++;
        }
    }

    /**
     * Delete a subject from the lecturer's teaching list and update the file.
     * @param subject Subject to delete
     */
    private void deleteSubject(Subject subject) {
        ArrayList<String> newCourse = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/lecturerTakeSubjectToTeach.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String myLine = line;
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String lecturerID = parts[0].trim();
                    String code = parts[1].trim();
                    int creditHours = Integer.parseInt(parts[2].trim());
                    String name = parts[3].trim();
                    if (this.id.equals(lecturerID) && subject.getCode().equals(code) && subject.getName().equals(name) && subject.getCreditHour() == creditHours) {
                        // Skip the line if it matches the subject to be deleted
                    } else {
                        newCourse.add(myLine + "\n"); // Add the line to the new course list if it does not match
                    }
                }
            }
            if (!newCourse.isEmpty()) {
                updateFileAfterDelation(newCourse); // Update the file with the new course list
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the lecturerTakeSubjectToTeach.csv file after a subject has been deleted.
     * @param course Updated list of courses
     */
    private void updateFileAfterDelation(ArrayList<String> course) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/lecturerTakeSubjectToTeach.csv"))) {
            for (String line : course) {
                writer.write(line); // Write each line to the file
            }
            System.out.println("Successfully dropped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all available subjects from the subjectList.csv file.
     * @throws IOException If an I/O error occurs
     */
    public void getSubjectsFromFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/subjectList.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String code = parts[0].trim();
                    int creditHours = Integer.parseInt(parts[1].trim());
                    String name = parts[2].trim();
                    allSubjects.add(new Subject(code, name,false, creditHours)); // Add each subject to the list of all subjects
                }
            }
        }
    }

    /**
     * List all available subjects.
     */
    public void listSubjects() {
        System.out.println("Code\tCredit Hours\tName");
        System.out.println("--------------------------------------");
        int i = 1;
        for (Subject subject : allSubjects) {
            System.out.println("No" + i + "  " + subject.getCode() + " \t" + subject.getCreditHour() + "\t" + subject.getName());
            i++;
        }
    }
}
