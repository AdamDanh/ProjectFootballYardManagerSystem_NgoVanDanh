package dfootballmanager;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private FootballList footballList = new FootballList();

    public Main() {
        setTitle("Football Yard Management - Main");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Management");
        JMenuItem addItem = new JMenuItem("Add Football Yard");
        JMenuItem updateItem = new JMenuItem("Update Football Yard");
        JMenuItem deleteItem = new JMenuItem("Delete Football Yard");
        JMenuItem displayItem = new JMenuItem("Display All Yards");
        JMenuItem calcItem = new JMenuItem("Calculate Cost by ID");
        JMenuItem exitItem = new JMenuItem("Exit");

        menu.add(addItem);
        menu.add(updateItem);
        menu.add(deleteItem);
        menu.add(displayItem);
        menu.add(calcItem);
        menu.addSeparator();
        menu.add(exitItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        JTextArea textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        displayItem.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Football f : footballList.getFootballList()) {
                sb.append(f.toString()).append("\n");
            }
            textArea.setText(sb.toString());
        });

        exitItem.addActionListener(e -> System.exit(0));
    }
}
