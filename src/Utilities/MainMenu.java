package Utilities;

import java.util.Scanner;

public class MainMenu {
    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        int n;
        ClearScreen.clearScreen();
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
        n = scanner.nextInt();
        switch (n) {
            case 1:
                StudentMenu.studentMenu();
                break;
            case 2:
                LecturerMenu.lecturerMenu();
                break;
            case 3:
                AdminMenu.adminMenu();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                // System.out.println("\n\n\nWRONG OPTION!");
                menu();
        }
        scanner.close();
    }
}
