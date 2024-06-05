package Utilities;

import java.util.Scanner;

public class StudentMenu {
    public static void studentMenu() {
        Scanner scanner = new Scanner(System.in);
        int n;

        ClearScreen.clearScreen();
        System.out.println("******************************");
        System.out.println("           STUDENT");
        System.out.println("******************************\n");
        System.out.println("        ------------");
        System.out.println("            MENU");
        System.out.println("        ------------\n");
        System.out.println("  1. Search subject");
        System.out.println("  2. Register Subject");
        System.out.println("  3. Drop Subject");
        System.out.println("  4. List of Subjects");
        System.out.println("  5. Back to Main Menu\n");
        System.out.print("   ENTER YOUR OPTION -> ");
        n = scanner.nextInt();
        switch (n) {
            case 1:
                // studentMenu();
                break;
            case 2:
                // studentMenu();
                break;
            case 3:
                // studentMenu();
                break;
            case 4:
                // studentMenu();
                break;
            case 5:
                MainMenu.menu();
                break;
            default:
                // System.out.println("\n\n\nWRONG OPTION!");
                studentMenu();
        }
        scanner.close();
    }
}
