package clinicmanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ShowPrescriptionPanel extends JPanel {
    JTable table;
    DefaultTableModel model;

    public ShowPrescriptionPanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Prescription List");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(30);

        model.setColumnIdentifiers(new String[]{
                "Prescription ID", "Patient", "Doctor", "Date", "Diagnosis", "Notes", "Medicines"
        });

        loadPrescriptions();

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
    }

    private void loadPrescriptions() {
        try {
            Conn conn = new Conn();
            String q = "SELECT p.prescription_id, " +
                    "CONCAT(pt.given_name, ' ', pt.family_name) AS patient, " +
                    "d.name AS doctor, p.date, p.diagnosis, p.notes " +
                    "FROM prescriptions_tbl p " +
                    "JOIN patient_tbl pt ON p.patient_id = pt.patient_id " +
                    "JOIN doctors_tbl d ON p.doctor_id = d.doctor_id";

            ResultSet rs = conn.stm.executeQuery(q);

            while (rs.next()) {
                String prescriptionId = rs.getString("prescription_id");
                String patient = rs.getString("patient");
                String doctor = rs.getString("doctor");
                String date = rs.getString("date");
                String diagnosis = rs.getString("diagnosis");
                String notes = rs.getString("notes");

                StringBuilder medicineList = new StringBuilder();
                PreparedStatement medStmt = conn.conn.prepareStatement("SELECT m.name, pm.dosage FROM prescription_medicines pm JOIN medicine_tbl m ON pm.medicine_id = m.medicine_id WHERE pm.prescription_id = ?");
                medStmt.setString(1, prescriptionId);
                ResultSet medRs = medStmt.executeQuery();
                while (medRs.next()) {
                    medicineList.append(medRs.getString("name"))
                            .append(" - ")
                            .append(medRs.getString("dosage"))
                            .append(", ");
                }
                if (medicineList.length() > 0)
                    medicineList.setLength(medicineList.length() - 2); // remove last comma

                model.addRow(new Object[]{
                        prescriptionId, patient, doctor, date, diagnosis, notes, medicineList.toString()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading prescriptions: " + e.getMessage());
        }
    }
}
