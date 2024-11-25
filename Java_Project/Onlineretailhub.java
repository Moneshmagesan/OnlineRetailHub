import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ECommerceDBAppGUI {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ECommerceDB";
    private static final String USER = "root";
    private static final String PASSWORD = "rmallukarthi95@";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("E-Commerce Database App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 1));

            JButton insertUserButton = new JButton("Insert User");
            JButton updateUserButton = new JButton("Update User");
            JButton deleteUserButton = new JButton("Delete User");
            JButton insertProductButton = new JButton("Insert Product");
            JButton exitButton = new JButton("Exit");

            panel.add(insertUserButton);
            panel.add(updateUserButton);
            panel.add(deleteUserButton);
            panel.add(insertProductButton);
            panel.add(exitButton);

            frame.add(panel);
            frame.setVisible(true);

            insertUserButton.addActionListener(e -> openInsertUserDialog());
            updateUserButton.addActionListener(e -> openUpdateUserDialog());
            deleteUserButton.addActionListener(e -> openDeleteUserDialog());
            insertProductButton.addActionListener(e -> openInsertProductDialog());
            exitButton.addActionListener(e -> System.exit(0));
        });
    }

    private static void openInsertUserDialog() {
        JFrame insertFrame = new JFrame("Insert User");
        insertFrame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField usernameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField fullNameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JButton submitButton = new JButton("Submit");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Full Name:"));
        panel.add(fullNameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumberField);
        panel.add(submitButton);

        insertFrame.add(panel);
        insertFrame.setVisible(true);

        submitButton.addActionListener(e -> {
            try (Connection conn = connect()) {
                String query = "INSERT INTO Users (Username, Email, Password, Full_Name, Address, Phone_Number) VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, usernameField.getText());
                    pstmt.setString(2, emailField.getText());
                    pstmt.setString(3, new String(passwordField.getPassword()));
                    pstmt.setString(4, fullNameField.getText());
                    pstmt.setString(5, addressField.getText());
                    pstmt.setString(6, phoneNumberField.getText());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(insertFrame, "User inserted successfully!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(insertFrame, "Error inserting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void openUpdateUserDialog() {
        JFrame updateFrame = new JFrame("Update User");
        updateFrame.setSize(400, 200);
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JTextField userIdField = new JTextField();
        JTextField emailField = new JTextField();
        JButton submitButton = new JButton("Submit");

        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("New Email:"));
        panel.add(emailField);
        panel.add(submitButton);

        updateFrame.add(panel);
        updateFrame.setVisible(true);

        submitButton.addActionListener(e -> {
            try (Connection conn = connect()) {
                String query = "UPDATE Users SET Email = ? WHERE User_ID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, emailField.getText());
                    pstmt.setInt(2, Integer.parseInt(userIdField.getText()));
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(updateFrame, "User updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(updateFrame, "No user found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(updateFrame, "Error updating user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void openDeleteUserDialog() {
        JFrame deleteFrame = new JFrame("Delete User");
        deleteFrame.setSize(400, 200);
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField userIdField = new JTextField();
        JButton submitButton = new JButton("Submit");

        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(submitButton);

        deleteFrame.add(panel);
        deleteFrame.setVisible(true);

        submitButton.addActionListener(e -> {
            try (Connection conn = connect()) {
                String query = "DELETE FROM Users WHERE User_ID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, Integer.parseInt(userIdField.getText()));
                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(deleteFrame, "User deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(deleteFrame, "No user found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(deleteFrame, "Error deleting user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void openInsertProductDialog() {
        JFrame insertFrame = new JFrame("Insert Product");
        insertFrame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField productNameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField stockField = new JTextField();
        JTextField categoryIdField = new JTextField();
        JButton submitButton = new JButton("Submit");

        panel.add(new JLabel("Product Name:"));
        panel.add(productNameField);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Stock:"));
        panel.add(stockField);
        panel.add(new JLabel("Category ID:"));
        panel.add(categoryIdField);
        panel.add(submitButton);

        insertFrame.add(panel);
        insertFrame.setVisible(true);

        submitButton.addActionListener(e -> {
            try (Connection conn = connect()) {
                String query = "INSERT INTO Products (Product_Name, Description, Price, Stock, Category_ID) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setString(1, productNameField.getText());
                    pstmt.setString(2, descriptionField.getText());
                    pstmt.setDouble(3, Double.parseDouble(priceField.getText()));
                    pstmt.setInt(4, Integer.parseInt(stockField.getText()));
                    pstmt.setInt(5, Integer.parseInt(categoryIdField.getText()));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(insertFrame, "Product inserted successfully!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(insertFrame, "Error inserting product: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
