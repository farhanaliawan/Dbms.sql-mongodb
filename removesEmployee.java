package com.mycompany.ems;

import org.bson.Document;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class removesEmployee extends JFrame implements ActionListener {

    Choice cEmpId;
    JButton delete, back;
    JLabel lblname, lblphone, lblemail;

    removesEmployee() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
//        ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/delete.png"));
//        Image i2 = i1.getImage().getScaledInstance(1100, 700, Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        JLabel image = new JLabel(i3);
//        image.setBounds(50, 100, 1050, 500);
//        add(image);
        

        JLabel labelempId = new JLabel("Employee Id");
        labelempId.setBounds(50, 50, 100, 30);
        add(labelempId);

        cEmpId = new Choice();
        cEmpId.setBounds(200, 50, 150, 30);
        add(cEmpId);

        try {
            conn1 c = new conn1();
            // Using the conn1 class to get employee IDs
            for (String empId : c.getEmployeeIds()) {
                cEmpId.add(empId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 100, 100, 30);
        add(labelname);

        lblname = new JLabel();
        lblname.setBounds(200, 100, 100, 30);
        add(lblname);

        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(50, 150, 100, 30);
        add(labelphone);

        lblphone = new JLabel();
        lblphone.setBounds(200, 150, 100, 30);
        add(lblphone);

        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 200, 100, 30);
        add(labelemail);

        lblemail = new JLabel();
        lblemail.setBounds(200, 200, 100, 30);
        add(lblemail);

        // Initial data load based on the selected item
        loadData();

        cEmpId.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                // Load data when the item is changed
                loadData();
            }
        });

        delete = new JButton("Delete");
        delete.setBounds(80, 300, 100, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

//        ImageIcon i1 = new ImageIcon(System.getProperty("icons/delete.png"));
//        Image i2 = i1.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        JLabel image = new JLabel(i3);
//        image.setBounds(350, 0, 600, 400);
//        add(image);

        setSize(1000, 400);
        setLocation(300, 150);
        setVisible(true);
    }

    // Load data based on the selected item
    private void loadData() {
        try {
            conn1 c = new conn1();
            // Using the conn1 class to get employee details by ID
            Iterable<Document> employeeData = c.getEmployeeById(cEmpId.getSelectedItem());
            for (Document document : employeeData) {
                lblname.setText(document.getString("name"));
                lblphone.setText(document.getString("phone"));
                lblemail.setText(document.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == delete) {
            try {
                conn1 c = new conn1();
                // Using the conn1 class to delete an employee by ID
                c.deleteEmployeeById(cEmpId.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Employee Information Deleted Successfully");
                setVisible(false);
                new Home1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Home1();
        }
    }

    public static void main(String[] args) {
        new removesEmployee();
    }
}
