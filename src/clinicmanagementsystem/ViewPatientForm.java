/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicmanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewPatientForm extends JFrame {

    
    
    public JTextField patientIdField, familyNameField, givenNameField, patientEmailField, patientPhoneField,
            mobileNumberField, postCodeField, addressField, sexField, birthDateField, bloodGroupField,
            emgFamilyNameField, emgGivenNameField, emgPhoneField, emgMobileField,
            createByField, createDateField;
JPanel mainPanel;

    public ViewPatientForm(String ID) {
         mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));

         
    
        
        
        setResizable(false);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color fgColor = new Color(50, 50, 50);

        JLabel title = new JLabel("View Patient");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(fgColor);
        title.setBounds(230, 15, 300, 30);
        mainPanel.add(title);

        int x1 = 30, x2 = 330, y = 60, gap = 35;

        // Left Column
        addLabel("Patient ID", x1, y, labelFont, fgColor); patientIdField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Family Name", x1, y, labelFont, fgColor); familyNameField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Given Name", x1, y, labelFont, fgColor); givenNameField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Email", x1, y, labelFont, fgColor); patientEmailField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Phone", x1, y, labelFont, fgColor); patientPhoneField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Mobile", x1, y, labelFont, fgColor); mobileNumberField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Post Code", x1, y, labelFont, fgColor); postCodeField = addField(x1 + 100, y, fieldFont, fgColor);
        y += gap;
        addLabel("Address", x1, y, labelFont, fgColor); addressField = addField(x1 + 100, y, fieldFont, fgColor);

        // Right Column
        y = 60;
        addLabel("Sex", x2, y, labelFont, fgColor); sexField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Birth Date", x2, y, labelFont, fgColor); birthDateField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Blood Group", x2, y, labelFont, fgColor); bloodGroupField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Emg. Family Name", x2, y, labelFont, fgColor); emgFamilyNameField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Emg. Given Name", x2, y, labelFont, fgColor); emgGivenNameField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Emg. Phone", x2, y, labelFont, fgColor); emgPhoneField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Emg. Mobile", x2, y, labelFont, fgColor); emgMobileField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Create By", x2, y, labelFont, fgColor); createByField = addField(x2 + 125, y, fieldFont, fgColor);
        y += gap;
        addLabel("Create Date", x2, y, labelFont, fgColor); createDateField = addField(x2 + 125, y, fieldFont, fgColor);
        
        // Disable editing if it's view-only
    for (JTextField field : new JTextField[]{patientIdField, familyNameField, givenNameField, patientEmailField, patientPhoneField,
            mobileNumberField, postCodeField, addressField, sexField, birthDateField, bloodGroupField,
            emgFamilyNameField, emgGivenNameField, emgPhoneField, emgMobileField,
            createByField, createDateField}) {
        field.setEditable(false);
        field.setBackground(Color.WHITE);
    }
            

     loadPatientDetails(ID);
        add(mainPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void loadPatientDetails(String patientId){
       
    try {
        Conn conn = new Conn(); // your DB connection class

        String sql = "SELECT * FROM patient_tbl WHERE patient_id = ?";
        PreparedStatement pst = conn.conn.prepareStatement(sql);
        pst.setString(1, patientId);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            patientIdField.setText(rs.getString("patient_id"));
            familyNameField.setText(rs.getString("family_name"));
            givenNameField.setText(rs.getString("given_name"));
            patientEmailField.setText(rs.getString("patient_email"));
            patientPhoneField.setText(rs.getString("patient_phone"));
            mobileNumberField.setText(rs.getString("mobile_number"));
            postCodeField.setText(rs.getString("post_code"));
            addressField.setText(rs.getString("address"));
            sexField.setText(rs.getString("sex"));
            birthDateField.setText(rs.getString("birth_date"));
            bloodGroupField.setText(rs.getString("blood_group"));
            emgFamilyNameField.setText(rs.getString("emg_family_name"));
            emgGivenNameField.setText(rs.getString("emg_given_name"));
            emgPhoneField.setText(rs.getString("emg_phone"));
            emgMobileField.setText(rs.getString("emg_mobile"));
            createByField.setText(rs.getString("create_by"));
            createDateField.setText(rs.getString("create_date"));
        } else {
            JOptionPane.showMessageDialog(this, "Patient not found.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading patient: " + e.getMessage());
    }

    }


    private void addLabel(String text, int x, int y, Font font, Color fg) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 150, 25);
        label.setFont(font);
        label.setForeground(fg);
        mainPanel.add(label);
    }

    private JTextField addField(int x, int y, Font font, Color fg) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 150, 25);
        tf.setFont(font);
        tf.setForeground(fg);
        mainPanel.add(tf);
        return tf;
    }

    
}

