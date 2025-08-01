package clinicmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ProfilePanel extends JPanel {
    JLabel nameLabel, emailLabel, phoneLabel, roleLabel, profilePic;
    int empID;
    String username;
            JButton editProfileBtn;


    public ProfilePanel(String username) {
        this.username = username;
        loadEmployeeID();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("My Profile", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        int row = 0;
        editProfileBtn = new JButton("Edit Profile");
editProfileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
editProfileBtn.setFocusPainted(false);
editProfileBtn.setBackground(new Color(100, 149, 237));
editProfileBtn.setForeground(Color.WHITE);

JPanel btnPanel = new JPanel();
btnPanel.setBackground(Color.WHITE);
btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
btnPanel.add(editProfileBtn);

add(btnPanel, BorderLayout.SOUTH);

 editProfileBtn.addActionListener(e -> {
    new EditProfileDialog(empID, this).setVisible(true);
});

        
        // Profile Picture
        profilePic = new JLabel();
        profilePic.setIcon(new ImageIcon(new ImageIcon(
                getClass().getResource("/icons/user.png"))  // Replace with your actual image
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        contentPanel.add(profilePic, gbc);
        row++;

        gbc.gridwidth = 1;

        addLabel("Name:", 0, row, labelFont, gbc, contentPanel);
        nameLabel = addValueLabel(1, row++, valueFont, gbc, contentPanel);

        addLabel("Email:", 0, row, labelFont, gbc, contentPanel);
        emailLabel = addValueLabel(1, row++, valueFont, gbc, contentPanel);

        addLabel("Phone:", 0, row, labelFont, gbc, contentPanel);
        phoneLabel = addValueLabel(1, row++, valueFont, gbc, contentPanel);

        addLabel("Role:", 0, row, labelFont, gbc, contentPanel);
        roleLabel = addValueLabel(1, row++, valueFont, gbc, contentPanel);

        add(contentPanel, BorderLayout.CENTER);

        loadProfileData();
    }

    private void addLabel(String text, int x, int y, Font font, GridBagConstraints gbc, JPanel panel) {
        gbc.gridx = x;
        gbc.gridy = y;
        JLabel label = new JLabel(text);
        label.setFont(font);
        panel.add(label, gbc);
    }

    private JLabel addValueLabel(int x, int y, Font font, GridBagConstraints gbc, JPanel panel) {
        gbc.gridx = x;
        gbc.gridy = y;
        JLabel value = new JLabel("Loading...");
        value.setFont(font);
        panel.add(value, gbc);
        return value;
    }
private void loadEmployeeID(){
    try{
       Conn conn = new Conn();
       ResultSet rs = conn.stm.executeQuery("SELECT * FROM users_tbl where username = '"+username+"'");
       if(rs.next()){
           this.empID = rs.getInt("employee_id");
       }
    }
    catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading Employee ID: " + e.getMessage());
        } 
}
    private void loadProfileData() {
        try {
            Conn conn = new Conn();
            String q = "SELECT * FROM employees WHERE employee_id = ?";
            PreparedStatement pst = conn.conn.prepareStatement(q);
            pst.setInt(1, empID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nameLabel.setText(rs.getString("first_name")+" "+rs.getString("last_name"));
                emailLabel.setText(rs.getString("email"));
                phoneLabel.setText(rs.getString("phone_number"));
                roleLabel.setText(rs.getString("role"));
            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }
            rs.close();
            pst.close();
            conn.conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading profile: " + e.getMessage());
        }
    }

}
class EditProfileDialog extends JDialog {
    JTextField usernameField, emailField;
    JPasswordField passwordField;
    int employeeId;
    ProfilePanel parent;

    public EditProfileDialog(int empId, ProfilePanel parentPanel) {
        this.employeeId = empId;
        this.parent = parentPanel;

        setTitle("Edit Account Info");
        setModal(true);
        setSize(350, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("New Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton saveBtn = new JButton("Save");
        panel.add(saveBtn);
        panel.add(new JLabel()); // empty space

        add(panel);

        loadCurrentUserData();

        saveBtn.addActionListener(e -> {
            if (validateFields()) {
                saveChanges();
            }
        });
    }

    private void loadCurrentUserData() {
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM users_tbl WHERE employee_id = ?";
            PreparedStatement pst = conn.conn.prepareStatement(query);
            pst.setInt(1, employeeId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                usernameField.setText(rs.getString("username"));
                emailField.setText(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (usernameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and Email are required.");
            return false;
        }

        String email = emailField.getText().trim();
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return false;
        }

        return true;
    }

    private void saveChanges() {
        try {
            Conn conn = new Conn();
            String sql = "UPDATE users_tbl SET username = ?, email = ?, password = ? WHERE employee_id = ?";
            PreparedStatement pst = conn.conn.prepareStatement(sql);
            pst.setString(1, usernameField.getText().trim());
            pst.setString(2, emailField.getText().trim());
            pst.setString(3, new String(passwordField.getPassword())); // hashed password recommended
            pst.setInt(4, employeeId);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Account info updated.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating: " + e.getMessage());
        }
    }
}

