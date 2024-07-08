package com.mycompany.ems;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.bson.Document; // Import the Document class

public class ViewsEmployee extends JFrame implements ActionListener {

    JTable table;
    Choice cemployeeId;
    JButton search, print, update, back;
    conn1 connection;

    ViewsEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/view.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1100, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(50, 100, 1050, 500);
        add(image);
        JLabel searchlbl = new JLabel("Search by Employee Id");
        searchlbl.setBounds(20, 20, 150, 20);
        add(searchlbl);

        cemployeeId = new Choice();
        cemployeeId.setBounds(180, 20, 150, 20);
        add(cemployeeId);

        connection = new conn1();
        List<String> employeeIds = connection.getEmployeeIds();
        for (String empId : employeeIds) {
            cemployeeId.add(empId);
        }

        table = new JTable();

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 20);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(320, 70, 80, 20);
        back.addActionListener(this);
        add(back);

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String selectedEmpId = cemployeeId.getSelectedItem();

            if (selectedEmpId != null && !selectedEmpId.isEmpty()) {
                Iterable<Document> employeeData = connection.getEmployeeById(selectedEmpId);
                displayEmployeeDetails(employeeData);
            } else {
                System.out.println("Please select an employee ID");
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == update) {
            setVisible(false);
            // TODO: Implement the update functionality
            // Example: new UpdateEmployee(selectedEmpId);
        } else {
            setVisible(false);
            new Home1();
        }
    }

    private void displayEmployeeDetails(Iterable<Document> employeeData) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Father's Name");
        model.addColumn("Salary");
        model.addColumn("education");
        // Add more columns as needed

        for (Document document : employeeData) {
            Object[] rowData = {
                document.getString("name"),
                document.getString("fatherName"),
                document.getString("salary"),
                document.getString("education"),

                // Add more fields as needed
            };
            model.addRow(rowData);
        }

        // Set the model to the JTable
        table.setModel(model);
    }

    public static void main(String[] args) {
        new ViewsEmployee();
    }
}
