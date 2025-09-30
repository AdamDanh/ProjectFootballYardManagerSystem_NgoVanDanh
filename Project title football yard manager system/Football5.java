package dfootballmanager;

import java.util.Scanner;
import java.util.Date;

public class Football5 extends Football {

    private int capacity;

    public Football5() {
    }

    public Football5(int capacity) {
        this.capacity = capacity;
    }

    public Football5(int capacity, String id, String yardname, double basecost, Date checkindate, Date checkoutdate) {
        super(id, yardname, basecost, checkindate, checkoutdate);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void addyard() {
        super.addyard();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter capacity: ");
        while (true) {
            String line = sc.nextLine();
            try {
                setCapacity(Integer.parseInt(line));
                break;
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Invalid number, please re-enter capacity: ");
            }
        }
    }

    @Override
    public boolean updateyard() {
        super.updateyard();
        Scanner sc = new Scanner(System.in);
        System.out.print("Update capacity (current: " + getCapacity() + "): ");
        while (true) {
            String line = sc.nextLine();
            try {
                setCapacity(Integer.parseInt(line));
                break;
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Invalid number, please re-enter capacity: ");
            }
        }
        return true;
    }

    @Override
    public void displaydetails() {
        super.displaydetails();
        System.out.println("Capacity: " + getCapacity());
    }

    @Override
    public double calculatecost() {
        double cost = getBasecost() * numberstone();
        if (numberstone() >= 3) {
            System.out.println("[INFO] Applied 10% discount for booking 3 days or more.");
            cost *= 0.9;
        }
        if (capacity > 17) {
            System.out.println("[INFO] Extra charge applied for capacity > 17.");
            cost += 100000;
        }
        return cost;
    }
}
