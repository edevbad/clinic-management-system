package clinicmanagementsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class ViewBillFrame extends JFrame {
    JTextArea billArea;

    public ViewBillFrame(String appointmentId) {
        setTitle("Bill Details - Appointment " + appointmentId);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(billArea);
        add(scroll);

        generateBill(appointmentId);
    }

    private void generateBill(String appointmentId) {
        StringBuilder sb = new StringBuilder();
        double total = 0.0;

        try {
            Conn conn = new Conn();

            // Get doctor_id for this appointment
            String doctorId = "";
            String getDoctorSql = "SELECT doctor_id FROM appointment_tbl WHERE appointment_id = ?";
            PreparedStatement getDoctorPst = conn.conn.prepareStatement(getDoctorSql);
            getDoctorPst.setString(1, appointmentId);
            ResultSet doctorRs = getDoctorPst.executeQuery();
            if (doctorRs.next()) {
                doctorId = doctorRs.getString("doctor_id");
            }

            // Get doctor's fee
            double doctorFee = 0.0;
            String doctorName = "";
            String feeSql = "SELECT name, fees FROM doctors_tbl WHERE doctor_id = ?";
            PreparedStatement feePst = conn.conn.prepareStatement(feeSql);
            feePst.setString(1, doctorId);
            ResultSet feeRs = feePst.executeQuery();
            if (feeRs.next()) {
                doctorName = feeRs.getString("name");
                doctorFee = feeRs.getDouble("fees");
                total += doctorFee;
            }

            sb.append("Doctor: ").append(doctorName).append("\n");
            sb.append("Consultation Fee: ").append(doctorFee).append(" PKR\n\n");

            sb.append("Prescribed Medicines:\n");
            sb.append("-------------------------------------------\n");
            sb.append(String.format("%-20s %-8s %-8s\n", "Medicine", "Dosage", "Price"));
            sb.append("-------------------------------------------\n");

            // Fetch medicines
            String medSql = "SELECT m.name, mp.dosage, m.price " +
                            "FROM prescription_medicines mp " +
                            "JOIN medicine_tbl m ON mp.medicine_id = m.medicine_id "
                    + "JOIN prescriptions_tbl p ON p.prescription_id = mp.prescription_id " +
                            "WHERE p.appointment_id = ?";
            PreparedStatement medPst = conn.conn.prepareStatement(medSql);
            medPst.setString(1, appointmentId);
            ResultSet medRs = medPst.executeQuery();

            while (medRs.next()) {
                String name = medRs.getString("name");
                String dosage = medRs.getString("dosage");
                double price = medRs.getDouble("price");
                double lineTotal =   price;
                total += lineTotal;

                sb.append(String.format("%-20s %-8s %-8.2f\n", name, dosage,  lineTotal));
            }

            sb.append("-------------------------------------------\n");
            sb.append(String.format("%-30s %10.2f PKR", "Total Amount:", total));

            billArea.setText(sb.toString());

            conn.conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating bill: " + e.getMessage());
        }
    }
}
