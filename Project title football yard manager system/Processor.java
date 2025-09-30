package dfootballmanager;

import java.util.Scanner;

public class Processor {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FootballList footballList = new FootballList();

        while (true) {
            System.out.println("\n========== FOOTBALL YARD MANAGEMENT ==========");
            System.out.println("1.  Add a new Football 5");
            System.out.println("2.  Add a new Football 7");
            System.out.println("3.  Add a new Football 11");
            System.out.println("4.  Update a Football by ID");
            System.out.println("5.  Delete a Football by ID");
            System.out.println("6.  Find a Football by ID");
            System.out.println("7.  Display all Football Yards");
            System.out.println("8.  Find the most expensive Football");
            System.out.println("9.  Count yards by type");
            System.out.println("10. Export data to file");
            System.out.println("11. Calculate cost by ID");
            System.out.println("12. Exit");
            System.out.print("Choose an option: ");

            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("[ERROR] Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: {
                    Football5 f5 = new Football5();
                    footballList.addyard(f5);
                    break;
                }
                case 2: {
                    Football7 f7 = new Football7();
                    footballList.addyard(f7);
                    break;
                }
                case 3: {
                    Football11 f11 = new Football11();
                    footballList.addyard(f11);
                    break;
                }
                case 4: {
                    System.out.print("Enter football ID to update: ");
                    String id = sc.nextLine();
                    footballList.updateyard(id);
                    break;
                }
                case 5: {
                    System.out.print("Enter football ID to delete: ");
                    String id = sc.nextLine();
                    footballList.deleteyardbyID(id);
                    break;
                }
                case 6: {
                    System.out.print("Enter football ID to find: ");
                    String id = sc.nextLine();
                    footballList.finyardbyID(id);
                    break;
                }
                case 7: {
                    footballList.displaydetails();
                    break;
                }
                case 8: {
                    footballList.finmostexpensiveyard();
                    break;
                }
                case 9: {
                    footballList.countYard();
                    break;
                }
                case 10: {
                    System.out.print("Enter filename to export (e.g., yards.txt): ");
                    String filename = sc.nextLine();
                    footballList.exportToFile(filename);
                    break;
                }
                case 11: {
                    System.out.print("Enter football ID to calculate cost: ");
                    String id = sc.nextLine();
                    Football f = footballList.finyardbyID(id);
                    if (f != null) {
                        double cost = f.calculatecost();
                        System.out.println("[INFO] Total cost for yard ID " + id + " = " + cost + " VND");
                    } else {
                        System.out.println("[ERROR] Football yard not found!");
                    }
                    break;
                }
                case 12: {
                    System.out.println("[INFO] Exiting the program. Goodbye!");
                    sc.close();
                    return;
                }
                default: {
                    System.out.println("[WARNING] Invalid choice. Please try again.");
                }
            }
        }
    }
}
