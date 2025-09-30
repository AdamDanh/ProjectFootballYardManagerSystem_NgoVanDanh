package dfootballmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DFootballUI extends JFrame {

    private final FootballList footballList = new FootballList();
    private DefaultTableModel tableModel = null;
    private JTable table = null;

    private final JTextField tfId = new JTextField();
    private final JTextField tfName = new JTextField();
    private final JTextField tfBase = new JTextField();
    private final JTextField tfCheckin = new JTextField();
    private final JTextField tfCheckout = new JTextField();
    private final JComboBox<String> cbType = new JComboBox<>(new String[]{"5", "7", "11"});
    private final JLabel lblExtra = new JLabel("Capacity:");
    private final JTextField tfExtra = new JTextField();

    private final SimpleDateFormat fullFmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public DFootballUI() {
        setTitle("⚽ VIP Football Yard Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 620);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        JLabel title = new JLabel("⚽ Football Yard Manager", JLabel.LEFT);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        top.add(title, BorderLayout.WEST);

        JLabel logo = new JLabel();
        ImageIcon ic = loadIcon("/images/football.png", 56, 56);
        if (ic != null) {
            logo.setIcon(ic);
        }
        top.add(logo, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.34);
        split.setLeftComponent(buildFormPanel());
        split.setRightComponent(buildTablePanel());
        add(split, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        bottom.add(buildButton("Add", e -> onAdd()));
        bottom.add(buildButton("Update", e -> onUpdate()));
        bottom.add(buildButton("Delete", e -> onDelete()));
        bottom.add(buildButton("Find", e -> onFind()));
        bottom.add(buildButton("Calculate", e -> onCalculate()));
        bottom.add(buildButton("Export", e -> onExport()));
        bottom.add(buildButton("Refresh", e -> refreshTable()));
        bottom.add(buildButton("Exit", e -> System.exit(0)));
        add(bottom, BorderLayout.SOUTH);

        refreshTable();
    }

    private JPanel buildFormPanel() {
        JPanel p = new JPanel(new BorderLayout(6, 6));
        p.setBorder(BorderFactory.createTitledBorder("Yard Form"));
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0;
        g.gridy = 0;
        form.add(new JLabel("ID:"), g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(tfId, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        form.add(new JLabel("Name:"), g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(tfName, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        form.add(new JLabel("Base cost (per hour):"), g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(tfBase, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        form.add(new JLabel("Check-in (dd/MM/yyyy HH:mm):"), g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(tfCheckin, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        form.add(new JLabel("Check-out (dd/MM/yyyy HH:mm):"), g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(tfCheckout, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        form.add(new JLabel("Type (5/7/11):"), g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(cbType, g);

        g.gridx = 0;
        g.gridy++;
        g.weightx = 0;
        form.add(lblExtra, g);
        g.gridx = 1;
        g.weightx = 1;
        form.add(tfExtra, g);

        cbType.addActionListener(e -> {
            String t = (String) cbType.getSelectedItem();
            if ("5".equals(t)) {
                lblExtra.setText("Capacity:");
            } else if ("7".equals(t)) {
                lblExtra.setText("NumberPlayers:");
            } else {
                lblExtra.setText("TotalPlayers:");
            }
        });

        JPanel helpers = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> clearForm());
        JButton btnLoadSelected = new JButton("Load selected");
        btnLoadSelected.addActionListener(e -> loadSelectedToForm());
        helpers.add(btnClear);
        helpers.add(btnLoadSelected);

        p.add(form, BorderLayout.CENTER);
        p.add(helpers, BorderLayout.SOUTH);
        return p;
    }

    private JScrollPane buildTablePanel() {
        String[] cols = {"ID", "Name", "Type", "BaseCost", "Check-in", "Check-out", "Extra", "Hours", "Cost"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadSelectedToForm();
                }
            }
        });
        return new JScrollPane(table);
    }

    private JButton buildButton(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(110, 30));
        return b;
    }

    private JButton buildButton(String text, ActionListener act) {
        JButton b = buildButton(text);
        b.addActionListener(act);
        return b;
    }

    private void onAdd() {
        try {
            String id = tfId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID is required");
                return;
            }
            if (findById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID already exists");
                return;
            }

            String name = tfName.getText().trim();
            double base = Double.parseDouble(tfBase.getText().trim());
            Date in = parseFlexible(tfCheckin.getText().trim());
            Date out = parseFlexible(tfCheckout.getText().trim());
            String type = (String) cbType.getSelectedItem();
            int extra = Integer.parseInt(tfExtra.getText().trim());

            Football f;
            if ("5".equals(type)) {
                f = new Football5(extra, id, name, base, in, out);
            } else if ("7".equals(type)) {
                f = new Football7(extra, id, name, base, in, out);
            } else {
                f = new Football11(extra, id, name, base, in, out);
            }

            // add directly to footballList.fl to avoid calling console addyard()
            footballList.fl.add(f);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Added yard " + id);
            clearForm();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number: " + nfe.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding yard: " + ex.getMessage());
        }
    }

    private void onUpdate() {
        try {
            String id = tfId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID is required");
                return;
            }
            Football old = findById(id);
            if (old == null) {
                JOptionPane.showMessageDialog(this, "No yard with ID " + id);
                return;
            }

            String name = tfName.getText().trim();
            double base = Double.parseDouble(tfBase.getText().trim());
            Date in = parseFlexible(tfCheckin.getText().trim());
            Date out = parseFlexible(tfCheckout.getText().trim());
            String type = (String) cbType.getSelectedItem();
            int extra = Integer.parseInt(tfExtra.getText().trim());

            Football updated;
            if ("5".equals(type)) {
                updated = new Football5(extra, id, name, base, in, out);
            } else if ("7".equals(type)) {
                updated = new Football7(extra, id, name, base, in, out);
            } else {
                updated = new Football11(extra, id, name, base, in, out);
            }

            // replace
            for (int i = 0; i < footballList.fl.size(); i++) {
                if (footballList.fl.get(i).getId().equals(id)) {
                    footballList.fl.set(i, updated);
                    break;
                }
            }
            refreshTable();
            JOptionPane.showMessageDialog(this, "Updated yard " + id);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Invalid number: " + nfe.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating yard: " + ex.getMessage());
        }
    }

    private void onDelete() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            id = JOptionPane.showInputDialog(this, "Enter ID to delete:");
        }
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        if (footballList.deleteyardbyID(id)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Deleted " + id);
        } else {
            JOptionPane.showMessageDialog(this, "No yard with ID " + id);
        }
    }

    private void onFind() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            id = JOptionPane.showInputDialog(this, "Enter ID to find:");
        }
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        Football f = footballList.finyardbyID(id);
        if (f != null) {
            // fill form with found data
            fillFormFromFootball(f);
            JOptionPane.showMessageDialog(this, "Found and loaded " + id);
        } else {
            JOptionPane.showMessageDialog(this, "Not found");
        }
    }

    private void onCalculate() {
        String id = tfId.getText().trim();
        if (id.isEmpty()) {
            id = JOptionPane.showInputDialog(this, "Enter ID to calculate:");
        }
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        Football f = findById(id);
        if (f == null) {
            JOptionPane.showMessageDialog(this, "Not found");
            return;
        }
        double cost = f.calculatecost();
        JOptionPane.showMessageDialog(this, "Total cost for " + id + " = " + String.format("%,.0f VND", cost));
    }

    private void onExport() {
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File("yards_export.txt"));
        int res = jfc.showSaveDialog(this);
        if (res != JFileChooser.APPROVE_OPTION) {
            return;
        }
        String fname = jfc.getSelectedFile().getAbsolutePath();
        footballList.exportToFile(fname);
        JOptionPane.showMessageDialog(this, "Exported to " + fname);
    }

    // ---------- Helpers ----------
    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Football f : footballList.fl) {
            String type = (f instanceof Football5) ? "5" : (f instanceof Football7) ? "7" : "11";
            String extra = "";
            if (f instanceof Football5) {
                extra = String.valueOf(((Football5) f).getCapacity());
            }
            if (f instanceof Football7) {
                extra = String.valueOf(((Football7) f).getNumberofplayer());
            }
            if (f instanceof Football11) {
                extra = String.valueOf(((Football11) f).getTotalPlayers());
            }
            long hours = f.numberstone();
            double cost = f.calculatecost();
            tableModel.addRow(new Object[]{
                f.getId(), f.getYardname(), type, f.getBasecost(),
                (f.getCheckindate() != null) ? fullFmt.format(f.getCheckindate()) : "-",
                (f.getCheckoutdate() != null) ? fullFmt.format(f.getCheckoutdate()) : "-",
                extra, hours, String.format("%,.0f", cost)
            });
        }
    }

    private void clearForm() {
        tfId.setText("");
        tfName.setText("");
        tfBase.setText("");
        tfCheckin.setText("");
        tfCheckout.setText("");
        tfExtra.setText("");
    }

    private Football findById(String id) {
        for (Football f : footballList.fl) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    private void loadSelectedToForm() {
        int r = table.getSelectedRow();
        if (r < 0) {
            return;
        }
        String id = (String) tableModel.getValueAt(r, 0);
        Football f = findById(id);
        if (f != null) {
            fillFormFromFootball(f);
        }
    }

    private void fillFormFromFootball(Football f) {
        tfId.setText(f.getId());
        tfName.setText(f.getYardname());
        tfBase.setText(String.valueOf(f.getBasecost()));
        tfCheckin.setText(f.getCheckindate() != null ? fullFmt.format(f.getCheckindate()) : "");
        tfCheckout.setText(f.getCheckoutdate() != null ? fullFmt.format(f.getCheckoutdate()) : "");
        if (f instanceof Football5) {
            cbType.setSelectedItem("5");
            tfExtra.setText(String.valueOf(((Football5) f).getCapacity()));
        } else if (f instanceof Football7) {
            cbType.setSelectedItem("7");
            tfExtra.setText(String.valueOf(((Football7) f).getNumberofplayer()));
        } else if (f instanceof Football11) {
            cbType.setSelectedItem("11");
            tfExtra.setText(String.valueOf(((Football11) f).getTotalPlayers()));
        }
    }

    private Date parseFlexible(String s) throws Exception {
        if (s == null) {
            return null;
        }
        s = s.trim();
        if (s.isEmpty()) {
            return null;
        }

        if (s.matches("\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{2}")) {
            String[] parts = s.split(" ");
            String dmy = parts[0];
            String hm = parts[1];
            int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
            s = dmy + "/" + year + " " + hm;
        }
        return fullFmt.parse(s);
    }

    private ImageIcon loadIcon(String path, int w, int h) {
        try {
            java.net.URL url = getClass().getResource(path);
            if (url == null) {

                File f = new File(path.startsWith("/") ? path.substring(1) : path);
                if (!f.exists()) {
                    return null;
                }
                ImageIcon ic = new ImageIcon(f.getAbsolutePath());
                Image im = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                return new ImageIcon(im);
            }
            ImageIcon ic = new ImageIcon(url);
            Image im = ic.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(im);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DFootballUI gui = new DFootballUI();
            gui.setVisible(true);
        });
    }
}
