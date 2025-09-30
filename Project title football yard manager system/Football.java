package dfootballmanager;

import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Football implements IFootball {

    private String id;
    private String yardname;
    private double basecost;
    private Date checkindate;
    private Date checkoutdate;

    // Reuse one Scanner for the whole class to avoid mixing issues
    private static final Scanner sc = new Scanner(System.in); // IMPROVED

    public Football() {
    }

    public Football(String id, String yardname, double basecost, Date checkindate, Date checkoutdate) {
        this.id = id;
        this.yardname = yardname;
        this.basecost = basecost;
        this.checkindate = checkindate;
        this.checkoutdate = checkoutdate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYardname() {
        return yardname;
    }

    public void setYardname(String yardname) {
        this.yardname = yardname;
    }

    public double getBasecost() {
        return basecost;
    }

    public void setBasecost(double basecost) {
        this.basecost = basecost;
    }

    public Date getCheckindate() {
        return checkindate;
    }

    public void setCheckindate(Date checkindate) {
        this.checkindate = checkindate;
    }

    public Date getCheckoutdate() {
        return checkoutdate;
    }

    public void setCheckoutdate(Date checkoutdate) {
        this.checkoutdate = checkoutdate;
    }

    public void addyard() {
        System.out.print("Enter ID: ");
        setId(sc.nextLine());

        System.out.print("Enter Name: ");
        setYardname(sc.nextLine());

        System.out.print("Enter Basecost: ");
        while (true) {
            String baseCostLine = sc.nextLine(); 
            try {
                setBasecost(Double.parseDouble(baseCostLine));
                break;
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Invalid number. Enter Basecost again: ");
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
 
        try {
            System.out.print("Enter check-in date (dd/MM/yyyy HH:mm) or leave blank: ");
            String in = sc.nextLine().trim();
            if (!in.isEmpty()) {
                checkindate = sdf.parse(in);
            } else {
                checkindate = null;
            }

            System.out.print("Enter check-out date (dd/MM/yyyy HH:mm) or leave blank: ");
            String out = sc.nextLine().trim();
            if (!out.isEmpty()) {
                checkoutdate = sdf.parse(out);
            } else {
                checkoutdate = null;
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Date parsing error: " + e.getMessage());
        }
    }

    public boolean updateyard() {
        System.out.println("Update yard Name (current: " + getYardname() + "): ");
        setYardname(sc.nextLine());

        System.out.println("Update yard Basecost (current: " + getBasecost() + "): ");

        while (true) {
            String line = sc.nextLine();
            try {
                setBasecost(Double.parseDouble(line));
                break;
            } catch (NumberFormatException e) {
                System.out.print("[ERROR] Invalid number. Enter Basecost again: ");
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            System.out.print("Update check-in date (dd/MM/yyyy HH:mm) or leave blank (current: "
                    + (checkindate != null ? sdf.format(checkindate) : "n/a") + "): ");
            String in = sc.nextLine().trim();
            if (!in.isEmpty()) {
                checkindate = sdf.parse(in);
            }

            System.out.print("Update check-out date (dd/MM/yyyy HH:mm) or leave blank (current: "
                    + (checkoutdate != null ? sdf.format(checkoutdate) : "n/a") + "): ");
            String out = sc.nextLine().trim();
            if (!out.isEmpty()) {
                checkoutdate = sdf.parse(out);
            }

            return true; // success
        } catch (Exception e) {
            System.out.println("[ERROR] Invalid date format. Update aborted.");
            return false; // FIXED: return false on error
        }
    }

    public void displaydetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("ID= " + getId()
                + ", Name= " + getYardname()
                + ", Basecost= " + getBasecost()
                + ", Check-in= " + (getCheckindate() != null ? sdf.format(getCheckindate()) : "n/a")
                + ", Check-out= " + (getCheckoutdate() != null ? sdf.format(getCheckoutdate()) : "n/a"));
    }

    public abstract double calculatecost();

    public int numberstone() {
        if (getCheckindate() == null || getCheckoutdate() == null) {
            return 0;
        }
        long diff = getCheckoutdate().getTime() - getCheckindate().getTime();
        return (int) (diff / (1000 * 60 * 60)); 
    }

}
