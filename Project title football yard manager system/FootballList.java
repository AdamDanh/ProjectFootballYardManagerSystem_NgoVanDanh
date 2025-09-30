package dfootballmanager;

import java.util.ArrayList;
import java.io.FileWriter; //goi de su dung ham xuat file
import java.io.IOException;

public class FootballList {

    ArrayList<Football> fl = new ArrayList<>();

    // Add a new yard
    public void addyard(Football football) {
        football.addyard();
        fl.add(football);
        System.out.println("[SUCCESS] The football yard has been added to the list.");
    }

    // Update a yard by ID
    public boolean updateyard(String id) {
        for (Football football : fl) {
            if (football.getId().equals(id)) {
                football.updateyard();
                System.out.println("[SUCCESS] Information for yard with ID = " + id + " has been updated.");
                return true;
            }
        }
        System.out.println("[WARNING] No yard found with ID = " + id + " to update.");
        return false;
    }

    // Delete a yard by ID
    public boolean deleteyardbyID(String id) {
        for (Football football : fl) {
            if (football.getId().equals(id)) {
                fl.remove(football);
                System.out.println("[SUCCESS] Yard with ID = " + id + " has been removed from the list.");
                return true;
            }
        }
        System.out.println("[WARNING] No yard found with ID = " + id + " to delete.");
        return false;
    }

    // Find a yard by ID
    public Football finyardbyID(String id) {
        for (Football football : fl) {
            if (football.getId().equals(id)) {
                System.out.println("[INFO] Yard with ID = " + id + " has been found:");
                football.displaydetails();
                return football;
            }
        }
        System.out.println("[WARNING] No yard found with ID = " + id);
        return null;
    }

    // Display all yards
    public void displaydetails() {
        if (fl.isEmpty()) {
            System.out.println("[INFO] The list of football yards is currently empty.");
        } else {
            System.out.println("======= List of Football Yards =======");
            for (Football football : fl) {
                football.displaydetails();
                System.out.println("----------------------------------");
            }
        }
    }

    // Find the most expensive yard
    public Football finmostexpensiveyard() {
        if (fl.isEmpty()) {
            System.out.println("[INFO] The list is empty. No yard to compare.");
            return null;
        }
        Football max = fl.get(0);
        for (Football football : fl) {
            if (football.calculatecost() > max.calculatecost()) {
                max = football;
            }
        }
        System.out.println("[INFO] The most expensive yard is:");
        max.displaydetails();
        return max;
    }

    // Count yards by type
    public void countYard() {
        int football5 = 0;
        int football7 = 0;
        int football11 = 0;

        for (Football football : fl) {
            if (football instanceof Football5) {
                football5++;
            } else if (football instanceof Football7) {
                football7++;
            } else if (football instanceof Football11) {
                football11++;
            }
        }

        System.out.println("======= Yard Statistics =======");
        System.out.println("Football 5 yards : " + football5);
        System.out.println("Football 7 yards : " + football7);
        System.out.println("Football 11 yards: " + football11);
    }

    // Export list to a file
    public void exportToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Football football : fl) {
                writer.write("ID: " + football.getId() + "\n");
                writer.write("Name: " + football.getYardname() + "\n");
                writer.write("Cost: " + football.calculatecost() + "\n");
                writer.write("-------------------------------------\n");
            }
            System.out.println("[SUCCESS] Data has been exported to file: " + filename);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to export data: " + e.getMessage());
        }
    }

    Iterable<Football> getFootballList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

