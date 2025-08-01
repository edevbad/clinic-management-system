package clinicmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewEmployeeDetailsFrame extends JFrame {

    private JTextField idField, firstNameField, lastNameField, genderField, dobField,
            emailField, phoneField, addressField, roleField,
            hireDateField, salaryField, gradeField, statusField;

    public ViewEmployeeDetailsFrame(int employeeId) {
        setTitle("View Employee Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 400);
        setLocationRelativeTo(null);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int row = 0;

        // row 1
        addLabelAndField(formPanel, gbc, "ID", idField = new JTextField(), 0, row);
        addLabelAndField(formPanel, gbc, "First Name", firstNameField = new JTextField(), 2, row++);
        // row 2
        addLabelAndField(formPanel, gbc, "Last Name", lastNameField = new JTextField(), 0, row);
        addLabelAndField(formPanel, gbc, "Gender", genderField = new JTextField(), 2, row++);
        // row 3
        addLabelAndField(formPanel, gbc, "DOB", dobField = new JTextField(), 0, row);
        addLabelAndField(formPanel, gbc, "Email", emailField = new JTextField(), 2, row++);
        // row 4
        addLabelAndField(formPanel, gbc, "Phone", phoneField = new JTextField(), 0, row);
        addLabelAndField(formPanel, gbc, "Address", addressField = new JTextField(), 2, row++);
        // row 5
        addLabelAndField(formPanel, gbc, "Role", roleField = new JTextField(), 0, row);
        addLabelAndField(formPanel, gbc, "Hire Date", hireDateField = new JTextField(), 2, row++);
        // row 6
        addLabelAndField(formPanel, gbc, "Salary", salaryField = new JTextField(), 0, row);
        addLabelAndField(formPanel, gbc, "Grade", gradeField = new JTextField(), 2, row++);
        // row 7
        addLabelAndField(formPanel, gbc, "Status", statusField = new JTextField(), 0, row);

        // Disable all fields (view-only)
        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JTextField) ((JTextField) comp).setEditable(false);
        }

        // Close button
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(closeBtn);

        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        loadEmployee(employeeId);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText + ":"), gbc);

        gbc.gridx = gridx + 1;
        gbc.gridwidth = 1;
        field.setColumns(12);
        panel.add(field, gbc);
    }

    private void loadEmployee(int id) {
        try {
            Conn conn = new Conn();
            String sql = "SELECT * FROM employees WHERE employee_id = ?";
            PreparedStatement pst = conn.conn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                idField.setText(String.valueOf(rs.getInt("employee_id")));
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                genderField.setText(rs.getString("gender"));
                dobField.setText(rs.getString("date_of_birth"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone_number"));
                addressField.setText(rs.getString("address"));
                roleField.setText(rs.getString("role"));
                hireDateField.setText(rs.getString("hire_date"));
                salaryField.setText(String.valueOf(rs.getDouble("salary")));
                gradeField.setText(rs.getString("grade"));
                statusField.setText(rs.getString("status"));
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found.");
                dispose();
            }

            rs.close();
            pst.close();
            conn.conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}
