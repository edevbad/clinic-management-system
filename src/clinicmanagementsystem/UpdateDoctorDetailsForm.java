package clinicmanagementsystem;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class UpdateDoctorDetailsForm extends JFrame{
    private JTextField doctorID;
    private JTextField doctorName;
    private JTextField designation;
    private JTextField department;
    private JTextField degrees;
    private JTextField createdAt;
    private JTextField birthDate;
    private JTextArea address;
    private JTextField email;
    private JTextField experience;
    private JTextField phoneNumber;
    private JTextField servicePlace;
    private JTextField specialist;
    private JButton updateBtn;
    private JButton cancelBtn;
    private JComboBox<String> bloodGroup;
private JComboBox<String> gender;

    
    UpdateDoctorDetailsForm(String dId){
    JPanel mainPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 10, 5, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Initialize fields
    doctorID = new JTextField(20);
    doctorName = new JTextField(20);
    designation = new JTextField(20);
    department = new JTextField(20);
    degrees = new JTextField(20);
    createdAt = new JTextField(20);
    birthDate = new JTextField(20);
    address = new JTextArea(3,4);
    email = new JTextField(20);
    experience = new JTextField(20);
    phoneNumber = new JTextField(20);
    servicePlace = new JTextField(20);
    specialist = new JTextField(20);
    bloodGroup = new JComboBox<>(new String[]{"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"});
    gender = new JComboBox<>(new String[]{"Male", "Female", "Other"});


    for (JTextField field : new JTextField[]{doctorID, doctorName, designation, department, degrees, createdAt,
             birthDate,  email, experience, phoneNumber, servicePlace, specialist}) {
        field.setBackground(Color.WHITE);
    }
    
    // Field labels and components
    String[] labels = {
        "Doctor ID:", "Name:", "Designation:", "Department:", "Degrees:", "Created At:",
        "Blood Group:", "Birth Date:", "Address:", "Email:", "Experience:",
        "Gender:", "Phone Number:", "Service Place:", "Specialist:"
    };

   JComponent[] fields = {
    doctorID, doctorName, designation, department, degrees, createdAt,
    bloodGroup, birthDate, address, email, experience,
    gender, phoneNumber, servicePlace, specialist
};



    // Layout rows
    for (int i = 0; i < labels.length; i++) {
       
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel(labels[i]), gbc);
            if (i == 8) { // Address field
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.WEST;
    JScrollPane addrScroll = new JScrollPane(address);
    addrScroll.setPreferredSize(new Dimension(200, 60));
    mainPanel.add(addrScroll, gbc);
    continue;
}

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        if (i == 6) {  // Blood Group
    mainPanel.add(bloodGroup, gbc);
} else if (i == 11) {  // Gender (10th index after email, experience)
    mainPanel.add(gender, gbc);
} else {
    mainPanel.add(fields[i], gbc);
}

    }
    doctorID.setEditable(false);
    createdAt.setEditable(false);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    updateBtn = new JButton("Update");
    cancelBtn = new JButton("Back");
    buttonPanel.add(updateBtn);
    buttonPanel.add(cancelBtn);
    
    updateBtn.addActionListener(e -> {
    if (doctorName.getText().trim().isEmpty() || designation.getText().trim().isEmpty() ||
        email.getText().trim().isEmpty() || phoneNumber.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all required fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        Conn conn = new Conn();
        String updateQuery = "UPDATE doctors_tbl SET name=?, designation=?, department=?, degrees=?, " +
                             "blood_group=?, birth_date=?, address=?, email=?, experience=?, gender=?, " +
                             "phone_number=?, service_place=?, specialist=? WHERE doctor_id=?";
        PreparedStatement pst = conn.conn.prepareStatement(updateQuery);
        pst.setString(1, doctorName.getText().trim());
        pst.setString(2, designation.getText().trim());
        pst.setString(3, department.getText().trim());
        pst.setString(4, degrees.getText().trim());
        pst.setString(5, bloodGroup.getSelectedItem().toString());
        pst.setString(6, birthDate.getText().trim());
        pst.setString(7, address.getText().trim());
        pst.setString(8, email.getText().trim());
        pst.setString(9, experience.getText().trim());
        pst.setString(10, gender.getSelectedItem().toString());
        pst.setString(11, phoneNumber.getText().trim());
        pst.setString(12, servicePlace.getText().trim());
        pst.setString(13, specialist.getText().trim());
        pst.setString(14, doctorID.getText().trim());

        int res = pst.executeUpdate();
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "Doctor details updated successfully!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed. Please try again.");
        }

        pst.close();
        conn.conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating doctor details:\n" + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
});

     
        gbc.gridx = 1;
        gbc.gridy = 15;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(buttonPanel, gbc);

    // Wrap with scroll if form is long
    JScrollPane scrollPane = new JScrollPane(mainPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Set content
    this.getContentPane().add(scrollPane);
    this.setSize(400, 700);
//    this.pack();
        setTitle("Doctor Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Optional: Set consistent font for all fields
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 14);
        for (JComponent comp : new JComponent[]{
                doctorID, doctorName, designation, department, degrees,
                createdAt, bloodGroup, birthDate, address, email,
                experience, gender, phoneNumber, servicePlace, specialist
        }) {
            comp.setFont(fieldFont);
        }

        // Load doctor details from DB
        loadDoctorDetails(dId);
    }

    private void loadDoctorDetails(String dId) {
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM doctors_tbl WHERE doctor_id = '" + dId +"'";
            ResultSet rs = conn.stm.executeQuery(query);

            if (rs.next()) {
                doctorID.setText(dId);
                doctorName.setText(rs.getString("name"));
                designation.setText(rs.getString("designation"));
                department.setText(rs.getString("department"));
                degrees.setText(rs.getString("degrees"));
                createdAt.setText(rs.getString("created_at"));
                bloodGroup.setSelectedItem(rs.getString("blood_group"));
                birthDate.setText(rs.getString("birth_date"));
                address.setText(rs.getString("address"));
                email.setText(rs.getString("email"));
                experience.setText(rs.getString("experience"));
                gender.setSelectedItem(rs.getString("gender"));
                phoneNumber.setText(rs.getString("phone_number"));
                servicePlace.setText(rs.getString("service_place"));
                specialist.setText(rs.getString("specialist"));
            } else {
                JOptionPane.showMessageDialog(this,
                        "No doctor found with ID: " + dId,
                        "Not Found", JOptionPane.WARNING_MESSAGE);
                dispose();
            }

            rs.close();
            conn.stm.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading doctor details:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}

