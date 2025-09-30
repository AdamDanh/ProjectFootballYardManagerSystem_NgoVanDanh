package dfootballmanager;

import java.util.Date;
import java.util.Scanner;

public class Football11 extends Football {

    private int totalPlayers; //Tong so cau tren san

    public Football11() {
    }

    public Football11(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public Football11(int totalPlayers, String id, String yardname, double basecost, Date checkindate, Date checkoutdate) {
        super(id, yardname, basecost, checkindate, checkoutdate);
        this.totalPlayers = totalPlayers;
    }

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    @Override
    public void addyard() {
        super.addyard();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter total players (both teams): ");
        while (true) {
            String line = sc.nextLine();
            try {
                setTotalPlayers(Integer.parseInt(line));
                break;
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Invalid number, please re-enter: ");
            }
        }
    }

    @Override
    public boolean updateyard() {
        super.updateyard();

        Scanner sc = new Scanner(System.in);
        System.out.print("Update total players (current: " + getTotalPlayers() + "): ");
        while (true) {
            String line = sc.nextLine();
            try {
                setTotalPlayers(Integer.parseInt(line));
                break;
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Invalid number, please re-enter: ");
            }
        }
        return true;
    }

    @Override
    public void displaydetails() {
        super.displaydetails();
        System.out.println("Total players: " + getTotalPlayers());
    }

    @Override
    public double calculatecost() {
        double cost = getBasecost() * numberstone();

        if (numberstone() >= 3) {
            System.out.println("[INFO] Applied 10% discount for booking 3 days or more.");
            cost *= 0.9;
        }

        if (totalPlayers > 22) {
            System.out.println("[INFO] Extra charge for more than 22 players.");
            cost += 200000;
        }

        return cost;
    }
}
