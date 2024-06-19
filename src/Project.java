import Actor.*;
import java.io.*;
import java.util.*;

public class Project {
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void menu() {
        clearScreen();
        System.out.println("*******************************************");
        System.out.println("        COURSE REGISTRATION SYSTEM");
        System.out.println("*******************************************\n");
        System.out.println("            -----------------");
        System.out.println("                MAIN MENU");
        System.out.println("            -----------------\n");
        System.out.println("              SELECT OPTION");
        System.out.println("-------------------------------------------");
        System.out.println("   1. STUDENT \t2. LECTURER \t3. ADMIN");
        System.out.println("-------------------------------------------");
        System.out.println("            (PRESS 4 TO EXIT)\n");
        System.out.print("         ENTER AN OPTION -> ");
    }

    public static void studentMenu() {
        clearScreen();
        System.out.println("******************************");
        System.out.println("           STUDENT");
        System.out.println("******************************\n");
        System.out.println("        ------------");
        System.out.println("            MENU");
        System.out.println("        ------------\n");
        System.out.println("  1. Search subject");
        System.out.println("  2. Register Subject");
        System.out.println("  3. Drop Subject");
        System.out.println("  4. List of Subjects\n");
        System.out.println(" 5. Back to Main Menu\n");
        System.out.print("   ENTER YOUR OPTION -> ");
    }

    public static void lecturerMenu() {
        clearScreen();
        System.out.println("******************************");
        System.out.println("            LECTURER");
        System.out.println("******************************\n");
        System.out.println("       ------------");
        System.out.println("           MENU");
        System.out.println("       ------------\n");
        System.out.println("  1. View Subject Details");
        System.out.println("  2. Choose Subject to Teach");
        System.out.println("  3. Drop Subject");
        System.out.println("  4. Subject Student List\n");
        System.out.println(" 5. Back to Main Menu\n");
        System.out.print("   ENTER YOUR OPTION -> ");
    }

    public static void adminMenu() {
        clearScreen();
        System.out.println("************************************");
        System.out.println("              ADMIN");
        System.out.println("************************************\n");
        System.out.println("          ------------");
        System.out.println("              MENU");
        System.out.println("          ------------\n");
        System.out.println("  1. List Subjects");
        System.out.println("  2. Manage Subject Sections");
        System.out.println("  3. Set Student Capacity");
        System.out.println("  4. Drop Subject/Course");
        System.out.println("  5. Confirm Course Registrations");
        System.out.println("  6. List Registered Students");
        System.out.println("  7. List Registered Lecturers");
        System.out.println("  8. Close Subjects\n");
        System.out.println(" 9. Back to Main Menu\n");
        System.out.print("   ENTER YOUR OPTION -> ");
    }

    public static ArrayList<Student> readStudent(Scanner inpFile) {
        ArrayList<Student> p = new ArrayList<>();
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String id = inpFile.next();
            String name = inpFile.nextLine();
            name = name.substring(1);

            p.add(new Student(id, name));
        }
        inpFile.close();
        return p;
    }

    public static Student studentLoginCheck(ArrayList<Student> per) {
        Scanner sc = new Scanner(System.in);
        Student s = null;
        boolean found = false;
        while (!found) {
            clearScreen();
            System.out.println("******************************");
            System.out.println("           STUDENT");
            System.out.println("******************************\n\n");
            System.out.print("      Enter ID: ");
            String id = sc.nextLine();
            for (Student p : per) {
                if (p.getId().equals(id)) {
                    System.out.println("\n\n    Welcome, " + p.getName());
                    s = new Student(p.getId(), p.getName());
                    found = true;
                    return s;
                }
            }
            if (!found) {
                System.out.println(" Wrong ID. Try Again");
            }
        }
        sc.close();
        return new Student("", "");
    }

    public static ArrayList<Lecturer> readLecturer(Scanner inpFile) {
        ArrayList<Lecturer> p = new ArrayList<>();
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String id = inpFile.next();
            String name = inpFile.nextLine();
            name = name.substring(1);

            p.add(new Lecturer(id, name));
        }
        inpFile.close();
        return p;
    }

    public static Lecturer lecturerLoginCheck(ArrayList<Lecturer> per) {
        Scanner sc = new Scanner(System.in);
        Lecturer l = null;
        boolean found = false;
        while (!found) {
            clearScreen();
            System.out.println("******************************");
            System.out.println("            LECTURER");
            System.out.println("******************************\n\n");
            System.out.print("     Enter ID: ");
            String id = sc.nextLine();
            for (Lecturer p : per) {
                if (p.getId().equals(id)) {
                    // System.out.println("\n\n\n Welcome, " + p.getName());
                    l = new Lecturer(p.getId(), p.getName());
                    found = true;
                    return l;
                }
            }
            if (!found) {
                System.out.println(" Wrong ID. Try Again");
            }
        }
        sc.close();
        return new Lecturer("", "");
    }

    public static ArrayList<Admin> readAdmin(Scanner inpFile) {
        ArrayList<Admin> p = new ArrayList<>();
        inpFile.useDelimiter(",|\\n");
        while (inpFile.hasNext()) {
            String id = inpFile.next();
            String name = inpFile.nextLine();
            name = name.substring(1);

            p.add(new Admin(id, name));
        }
        inpFile.close();
        return p;
    }

    public static Admin adminLoginCheck(ArrayList<Admin> per) {
        Scanner sc = new Scanner(System.in);
        Admin a = null;
        boolean found = false;
        while (!found) {
            System.out.println("************************************");
            System.out.println("              ADMIN");
            System.out.println("************************************\n\n");
            System.out.print("        Enter ID: ");
            String id = sc.nextLine();
            for (Admin p : per) {
                if (p.getId().equals(id)) {
                    // System.out.println("\n\n\n Welcome, " + p.getName());
                    a = new Admin(p.getId(), p.getName());
                    found = true;
                    return a;
                }
            }
            if (!found) {
                System.out.println(" Wrong ID. Try Again");
            }
        }
        sc.close();
        return new Admin("", "");
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        Scanner inpFile = new Scanner(new File("studentList.csv"));
        ArrayList<Student> student = readStudent(inpFile);
        inpFile = new Scanner(new File("lecturerList.csv"));
        ArrayList<Lecturer> lecturer = readLecturer(inpFile);
        inpFile = new Scanner(new File("adminList.csv"));
        ArrayList<Admin> admin = readAdmin(inpFile);
        
        Student stud;
        Lecturer lect;
        Admin adm;
        int choice;
        do {
            menu();
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    stud = studentLoginCheck(student);
                    sc.nextLine();
                    int n1;
                    do {
                        studentMenu();
                        n1 = sc.nextInt();
                        sc.nextLine();
                        switch (n1) {
                            case 1:
                                //studentMenu();
                                sc.nextLine();
                                break;
                            case 2:
                                //studentMenu();
                                sc.nextLine();
                                break;
                            case 3:
                                //studentMenu();
                                sc.nextLine();
                                break;
                            case 4:
                                //studentMenu();
                                sc.nextLine();
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("\n\n\tWRONG OPTION!\n\n");
                                sc.nextLine();
                        }
                    } while (!(n1 == 5));
                    break;
                case 2:
                    lect = lecturerLoginCheck(lecturer);
                    sc.nextLine();
                    int n2;
                    do {
                        lecturerMenu();
                        n2 = sc.nextInt();
                        sc.nextLine();
                        switch (n2) {
                            case 1:
                                lect.viewSubjectDetails();
                                sc.nextLine();
                                break;
                            case 2:
                                //lecturerMenu();
                                sc.nextLine();
                                break;
                            case 3:
                                //lecturerMenu();
                                sc.nextLine();
                                break;
                            case 4:
                                //lecturerMenu();
                                sc.nextLine();
                                break;
                            case 5:
                                break;
                            default:
                                System.out.println("\n\n\tWRONG OPTION!\n\n");
                                sc.nextLine();
                        }
                    } while (!(n2 == 5));
                    break;
                case 3:
                    adm = adminLoginCheck(admin);
                    sc.nextLine();
                    int n3;
                    do {
                        adminMenu();
                        n3 = sc.nextInt();
                        sc.nextLine();
                        switch (n3) {
                            case 1:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 2:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 3:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 4:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 5:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 6:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 7:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 8:
                                // adminMenu();
                                sc.nextLine();
                                break;
                            case 9:
                                break;
                            default:
                                System.out.println("\n\n\tWRONG OPTION!\n\n");
                                sc.nextLine();
                        }
                    } while (!(n3 == 9));
                    break;
                case 4:
                    System.out.println("\n\n    THANK YOU FOR USING THIS SYSTEM!\n\n");
            }
        } while (!(choice == 4));

        sc.close();
    }

}
