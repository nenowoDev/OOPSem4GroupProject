package Utilities;
import java.util.Scanner;


public class AdminMenu{
    public static void adminMenu() {
        Scanner scanner = new Scanner(System.in);
        int n;
        ClearScreen.clearScreen();
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
        System.out.println("  8. Close Subjects");
        System.out.println("  9. Back to Main Menu\n");
        System.out.print("   ENTER YOUR OPTION -> ");
        n = scanner.nextInt();
        switch (n) {
            case 1:
                // adminMenu();
                break;
            case 2:
                // adminMenu();
                break;
            case 3:
                // adminMenu();
                break;
            case 4:
                // adminMenu();
                break;
            case 5:
                // adminMenu();
                break;
            case 6:
                // adminMenu();
                break;
            case 7:
                // adminMenu();
                break;
            case 8:
                // adminMenu();
                break;
            case 9:
                MainMenu.menu();
                break;
            default:
                // System.out.println("\n\n\nWRONG OPTION!");
                adminMenu();
        }
        scanner.close();
    }
}