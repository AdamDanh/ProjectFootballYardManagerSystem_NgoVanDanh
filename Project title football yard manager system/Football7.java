package dfootballmanager;

import java.util.Date;
import java.util.Scanner;

public class Football7 extends Football {

    private int numberofplayer;

    public Football7() {
    }

    public Football7(int numberofplayer) {
        this.numberofplayer = numberofplayer;
    }

    public Football7(int numberofplayer, String id, String yardname, double basecost, Date checkindate, Date checkoutdate) {
        super(id, yardname, basecost, checkindate, checkoutdate);
        this.numberofplayer = numberofplayer;
    }

    public int getNumberofplayer() {
        return numberofplayer;
    }

    public void setNumberofplayer(int numberofplayer) {
        this.numberofplayer = numberofplayer;
    }

    @Override
    public void addyard() {
        super.addyard();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of players: ");

        while (true) {
            String line = sc.nextLine();
            try {
                setNumberofplayer(Integer.parseInt(line));
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

        System.out.print("Update number of players (current: " + getNumberofplayer() + "): ");

        while (true) {
            String line = sc.nextLine();
            try {
                setNumberofplayer(Integer.parseInt(line));
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
        System.out.println("Number of players: " + getNumberofplayer());
    }

    @Override
    public double calculatecost() {
        double cost = getBasecost() * numberstone();

        if (numberstone() >= 5) {
            System.out.println("[INFO] Applied 5% discount for booking 5 days or more.");
            cost *= 0.95;
        }
        if (numberofplayer > 18) {
            System.out.println("[INFO] Extra charge applied for more than 14 players.");
            cost += 150000;
        }

        return cost;
    }
}
