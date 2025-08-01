
package clinicmanagementsystem;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class ViewDoctorDetails extends JFrame{
    private JTextField doctorID;
    private JTextField doctorName;
    private JTextField designation;
    private JTextField department;
    private JTextField degrees;
    private JTextField createdAt;
    private JTextField bloodGroup;
    private JTextField birthDate;
    private JTextArea address;
    private JTextField email;
    private JTextField experience;
    private JTextField gender;
    private JTextField phoneNumber;
    private JTextField servicePlace;
    private JTextField specialist;
    
    ViewDoctorDetails(String dId){
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
    bloodGroup = new JTextField(20);
    birthDate = new JTextField(20);
    address = new JTextArea(3,4);
    email = new JTextField(20);
    experience = new JTextField(20);
    gender = new JTextField(20);
    phoneNumber = new JTextField(20);
    servicePlace = new JTextField(20);
    specialist = new JTextField(20);

    // Disable editing if it's view-only
    for (JTextField field : new JTextField[]{doctorID, doctorName, designation, department, degrees, createdAt,
            bloodGroup, birthDate,  email, experience, gender, phoneNumber, servicePlace, specialist}) {
        field.setEditable(false);
        field.setBackground(Color.WHITE);
    }
    address.setEditable(false);

    // Field labels and components
    String[] labels = {
        "Doctor ID:", "Name:", "Designation:", "Department:", "Degrees:", "Created At:",
        "Blood Group:", "Birth Date:", "Address:", "Email:", "Experience:",
        "Gender:", "Phone Number:", "Service Place:", "Specialist:"
    };

     JTextField[] fields = {
        doctorID, doctorName, designation, department, degrees, createdAt,
        bloodGroup, birthDate,email,  email, experience,
        gender, phoneNumber, servicePlace, specialist
    };

    // Layout rows
    for (int i = 0; i < labels.length; i++) {
       
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel(labels[i]), gbc);
            if(i == 8){
            gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(address, gbc);
        continue;
        }
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(fields[i], gbc);
    }

    // Wrap with scroll if form is long
    JScrollPane scrollPane = new JScrollPane(mainPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Set content
    this.getContentPane().add(scrollPane);
    this.setSize(400, 650);
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
            String query = "SELECT * FROM doctors_tbl WHERE doctor_id = '" + dId + "'";
            ResultSet rs = conn.stm.executeQuery(query);

            if (rs.next()) {
                doctorID.setText(String.valueOf(dId));
                doctorName.setText(rs.getString("name"));
                designation.setText(rs.getString("designation"));
                department.setText(rs.getString("department"));
                degrees.setText(rs.getString("degrees"));
                createdAt.setText(rs.getString("created_at"));
                bloodGroup.setText(rs.getString("blood_group"));
                birthDate.setText(rs.getString("birth_date"));
                address.setText(rs.getString("address"));
                email.setText(rs.getString("email"));
                experience.setText(rs.getString("experience"));
                gender.setText(rs.getString("gender"));
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

    // GUI Components (should be defined in initComponents)
    

}

    
