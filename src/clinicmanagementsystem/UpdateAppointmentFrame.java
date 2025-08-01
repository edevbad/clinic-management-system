package clinicmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateAppointmentFrame extends JFrame {
    JComboBox<String> patientCombo, doctorCombo, timeCombo;
    JTextField phoneField, dateField, problemField;
    JButton updateBtn, cancelBtn;

    String appointmentID;

    public UpdateAppointmentFrame(String appointmentID) {
        this.appointmentID = appointmentID;

        setTitle("Update Appointment - " + appointmentID);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 14);

        int row = 0;

        addLabel("Patient:", 0, row, labelFont, gbc);
        patientCombo = addComboBox(1, row++, valueFont, gbc);

        addLabel("Doctor:", 0, row, labelFont, gbc);
        doctorCombo = addComboBox(1, row++, valueFont, gbc);

        addLabel("Phone No:", 0, row, labelFont, gbc);
        phoneField = addTextField(1, row++, valueFont, gbc);

        addLabel("Date (YYYY-MM-DD):", 0, row, labelFont, gbc);
        dateField = addTextField(1, row++, valueFont, gbc);

        addLabel("Time:", 0, row, labelFont, gbc);
        timeCombo = addComboBox(1, row++, valueFont, gbc);
        loadTimeCombo();

        addLabel("Problem:", 0, row, labelFont, gbc);
        problemField = addTextField(1, row++, valueFont, gbc);

        updateBtn = new JButton("Update");
        cancelBtn = new JButton("Cancel");

        gbc.gridx = 0;
        gbc.gridy = row;
        add(updateBtn, gbc);

        gbc.gridx = 1;
        add(cancelBtn, gbc);

        updateBtn.addActionListener(e -> updateAppointment());
        cancelBtn.addActionListener(e -> dispose());

        loadData();
        setResizable(false);
        this.setVisible(true);
    }

    private void addLabel(String text, int x, int y, Font font, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        JLabel label = new JLabel(text);
        label.setFont(font);
        add(label, gbc);
    }

    private JTextField addTextField(int x, int y, Font font, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        JTextField tf = new JTextField(15);
        tf.setFont(font);
        add(tf, gbc);
        return tf;
    }

    private JComboBox<String> addComboBox(int x, int y, Font font, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        JComboBox<String> cb = new JComboBox<>();
        cb.setFont(font);
        add(cb, gbc);
        return cb;
    }

    private void loadTimeCombo() {
        String[] amPm = {"AM", "PM"};
        for (int hour = 9; hour <= 17; hour++) {
            for (int min = 0; min < 60; min += 30) {
                int displayHour = (hour == 12 || hour == 0) ? 12 : hour % 12;
                String timeStr = String.format("%02d:%02d %s", displayHour, min, amPm[hour / 12]);
                timeCombo.addItem(timeStr);
            }
        }
    }

    private void loadAvailableDoctors(String date, String time) {
        try {
            doctorCombo.removeAllItems();

            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
            String formattedTime = outputFormat.format(inputFormat.parse(time));

            Conn conn = new Conn();
            String q = "SELECT d.doctor_id, d.name FROM doctors_tbl d " +
                       "LEFT JOIN appointment_tbl a ON d.doctor_id = a.doctor_id " +
                       "AND a.appointment_date = ? AND TIME_FORMAT(a.appointment_time, '%H:%i') = ? " +
                       "WHERE a.appointment_id IS NULL";

            PreparedStatement pst = conn.conn.prepareStatement(q);
            pst.setString(1, date);
            pst.setString(2, formattedTime);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                doctorCombo.addItem(rs.getString("doctor_id") + " - " + rs.getString("name"));
            }

            pst.close();
            conn.conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAvailablePatients(String date, String time) {
    try {
        patientCombo.removeAllItems();

        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = outputFormat.format(inputFormat.parse(time));

        Conn conn = new Conn();

        // Fetch current appointment's patient_id
        String currentPatientId = null;
        String q1 = "SELECT patient_id FROM appointment_tbl WHERE appointment_id = ?";
        PreparedStatement ps1 = conn.conn.prepareStatement(q1);
        ps1.setString(1, appointmentID);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            currentPatientId = rs1.getString(1);
        }
        rs1.close();
        ps1.close();

        // Now fetch patients excluding those already booked — but include the current patient
        String q = "SELECT p.patient_id, p.given_name FROM patient_tbl p " +
                   "WHERE p.patient_id = ? OR p.patient_id NOT IN (" +
                   "SELECT patient_id FROM appointment_tbl WHERE appointment_date = ? AND TIME_FORMAT(appointment_time, '%H:%i') = ?)";

        PreparedStatement pst = conn.conn.prepareStatement(q);
        pst.setString(1, currentPatientId);
        pst.setString(2, date);
        pst.setString(3, formattedTime);

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            patientCombo.addItem(rs.getString("patient_id") + " - " + rs.getString("given_name"));
        }

        rs.close();
        pst.close();
        conn.conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private void loadData() {
        try {
            Conn conn = new Conn();
            String q = "SELECT * FROM appointment_tbl WHERE appointment_id = ?";
            PreparedStatement pst = conn.conn.prepareStatement(q);
            pst.setString(1, appointmentID);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                phoneField.setText(rs.getString("phone_number"));
                problemField.setText(rs.getString("problem"));
                dateField.setText(rs.getString("appointment_date"));

                Time time = rs.getTime("appointment_time");
                SimpleDateFormat outFormat = new SimpleDateFormat("hh:mm a");
                String timeStr = outFormat.format(new Date(time.getTime()));
                timeCombo.setSelectedItem(timeStr);

                loadAvailableDoctors(dateField.getText(), timeStr);
                loadAvailablePatients(dateField.getText(), timeStr);

                String patientId = rs.getString("patient_id");
                for (int i = 0; i < patientCombo.getItemCount(); i++) {
                    if (patientCombo.getItemAt(i).startsWith(patientId + " ")) {
                        patientCombo.setSelectedIndex(i);
                        break;
                    }
                }

                String doctorId = rs.getString("doctor_id");
                for (int i = 0; i < doctorCombo.getItemCount(); i++) {
                    if (doctorCombo.getItemAt(i).startsWith(doctorId + " ")) {
                        doctorCombo.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Appointment not found.");
                dispose();
            }

            rs.close();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAppointment() {
    try {
        // Validate required fields
        if (patientCombo.getSelectedItem() == null || doctorCombo.getSelectedItem() == null ||
            phoneField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty() ||
            problemField.getText().trim().isEmpty() || timeCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dateStr = dateField.getText().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        java.util.Date date;
        try {
            date = dateFormat.parse(dateStr);  // Validate date format
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String phone = phoneField.getText().trim();
        if (!phone.matches("\\d{7,15}")) {  // optional phone validation
            JOptionPane.showMessageDialog(this, "Phone number must be 7-15 digits.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Conn conn = new Conn();
        String q = "UPDATE appointment_tbl SET " +
                "patient_id=?, doctor_id=?, phone_number=?, problem=?, appointment_date=?, appointment_time=? " +
                "WHERE appointment_id=?";
        PreparedStatement pst = conn.conn.prepareStatement(q);

        String patientId = patientCombo.getSelectedItem().toString().split(" - ")[0];
        String doctorId = doctorCombo.getSelectedItem().toString().split(" - ")[0];

        pst.setString(1, patientId);
        pst.setString(2, doctorId);
        pst.setString(3, phone);
        pst.setString(4, problemField.getText().trim());
        pst.setDate(5, new java.sql.Date(date.getTime()));

        // Convert time to 24-hour format
        SimpleDateFormat in = new SimpleDateFormat("hh:mm a");
        java.util.Date parsed = in.parse(timeCombo.getSelectedItem().toString());
        pst.setTime(6, new java.sql.Time(parsed.getTime()));

        pst.setString(7, appointmentID);

        int res = pst.executeUpdate();
        if (res > 0) {
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed.");
        }

        pst.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}


    
}