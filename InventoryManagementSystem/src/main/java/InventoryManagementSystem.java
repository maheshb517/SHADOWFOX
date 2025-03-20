import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InventoryManagementSystem {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField nameField, quantityField, priceField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/InventoryDB";
    private static final String DB_USER = "root";  // Change to your MySQL username
    private static final String DB_PASSWORD = "mahesh"; // Change to your MySQL password

    public InventoryManagementSystem() {
        frame = new JFrame("Inventory Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Name", "Quantity", "Price"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loadInventory();

        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());

        frame.setVisible(true);
    }

    private void loadInventory() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inventory")) {
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"), rs.getDouble("price")});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading inventory: " + ex.getMessage());
        }
    }

    private void addItem() {
        String name = nameField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();

        if (!name.isEmpty() && !quantity.isEmpty() && !price.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO inventory (name, quantity, price) VALUES (?, ?, ?)")) {
                pstmt.setString(1, name);
                pstmt.setInt(2, Integer.parseInt(quantity));
                pstmt.setDouble(3, Double.parseDouble(price));
                pstmt.executeUpdate();
                loadInventory();
                nameField.setText("");
                quantityField.setText("");
                priceField.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error adding item: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill all fields.");
        }
    }

    private void updateItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String quantity = quantityField.getText();
            String price = priceField.getText();

            if (!name.isEmpty() && !quantity.isEmpty() && !price.isEmpty()) {
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement("UPDATE inventory SET name=?, quantity=?, price=? WHERE id=?")) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, Integer.parseInt(quantity));
                    pstmt.setDouble(3, Double.parseDouble(price));
                    pstmt.setInt(4, id);
                    pstmt.executeUpdate();
                    loadInventory();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error updating item: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Select a row to update.");
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) model.getValueAt(selectedRow, 0);
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM inventory WHERE id=?")) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                loadInventory();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error deleting item: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Select a row to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManagementSystem::new);
    }
}
