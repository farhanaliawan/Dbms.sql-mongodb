package com.mycompany.ems;

import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatesEmployee extends JFrame implements ActionListener {

    JFrame frame;
    JTable table;
    JScrollPane scrollPane;
    JButton backButton, updateButton;
    JTextField nameField, salaryField, addressField, phoneField, emailField;
    conn1 connection;

    DefaultTableModel model; // Add a member variable for the table model

    UpdatesEmployee() {
        frame = new JFrame("Employee Details");
        frame.setLayout(null);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        connection = new conn1();

        // Fetching data from the database
        Iterable<Document> documents = connection.getEmployeeData();

        // Converting Iterable<Document> to a 2D array for JTable
        String[][] data = new String[100][10];
        int i = 0;
        for (Document document : documents) {
            data[i][0] = document.getString("empId");
            data[i][1] = document.getString("name");
            data[i][2] = document.getString("fatherName");
            data[i][3] = document.getString("salary");
            data[i][4] = document.getString("address");
            data[i][5] = document.getString("phone");
            data[i][6] = document.getString("email");
            data[i][7] = document.getString("education");
            data[i][8] = document.getString("designation");
            data[i][9] = document.getString("cnic");
            i++;
        }

        // Column Names
        String[] columnNames = {"Emp ID", "Name", "Father's Name", "Salary", "Address", "Phone", "Email", "Education", "Designation", "CNIC"};

        // Initializing the JTable with DefaultTableModel
        model = new DefaultTableModel(data, columnNames); // Use the member variable
        table = new JTable(model);

        // Adding JScrollPane to the frame
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 20, 700, 300);
        frame.add(scrollPane);

        // Adding update button
        updateButton = new JButton("Update");
        updateButton.setBounds(100, 350, 100, 30);
        updateButton.addActionListener(this);
        frame.add(updateButton);

        // Adding back button
        backButton = new JButton("Back");
        backButton.setBounds(250, 350, 100, 30);
        backButton.addActionListener(this);
        frame.add(backButton);

        // Adding input fields for update
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 400, 50, 25);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 400, 150, 25);
        frame.add(nameField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(250, 400, 50, 25);
        frame.add(salaryLabel);

        salaryField = new JTextField();
        salaryField.setBounds(300, 400, 150, 25);
        frame.add(salaryField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(500, 400, 50, 25);
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(550, 400, 150, 25);
        frame.add(addressField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 450, 50, 25);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(100, 450, 150, 25);
        frame.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(250, 450, 50, 25);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(300, 450, 150, 25);
        frame.add(emailField);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == updateButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String empId = (String) table.getValueAt(selectedRow, 0);
                String newName = nameField.getText();
                String newSalary = salaryField.getText();
                String newAddress = addressField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();

                // Call the update method in conn1
                connection.updateEmployee(empId, newName, newSalary, newAddress, newPhone, newEmail);

                // Refresh the table after update
                refreshTable();

                JOptionPane.showMessageDialog(frame, "Employee with ID " + empId + " updated successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row to update.");
            }
        } else if (e.getSource() == backButton) {
            frame.dispose();
        }
    }

    private void refreshTable() {
        model.setRowCount(0); // Clear existing rows

        // Fetching data from the database
        Iterable<Document> documents = connection.getEmployeeData();

        int i = 0;
        for (Document document : documents) {
            if (i == 0) { // Add column names only once
                String[] columnNames = {"Emp ID", "Name", "Father's Name", "Salary", "Address", "Phone", "Email", "Education", "Designation", "CNIC"};
                model.addRow(columnNames);
            }

            model.addRow(new Object[]{
                    document.getString("empId"),
                    document.getString("name"),
                    document.getString("fatherName"),
                    document.getString("salary"),
                    document.getString("address"),
                    document.getString("phone"),
                    document.getString("email"),
                    document.getString("education"),
                    document.getString("designation"),
                    document.getString("cnic")
            });
            i++;
        }
    }

    public static void main(String[] args) {
        new UpdatesEmployee();
    }
}
