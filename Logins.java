package com.mycompany.ems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.bson.Document;
import com.mongodb.client.*;

public class Logins extends JFrame implements ActionListener {

    JTextField tfusername, tfpassword;

    Logins() {

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 30);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 30);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 30);
        add(lblpassword);

        tfpassword = new JPasswordField(); // Use JPasswordField for password fields
        tfpassword.setBounds(150, 70, 150, 30);
        add(tfpassword);

        JButton login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

ImageIcon i1 = new ImageIcon(getClass().getResource("/icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 200, 200);
        add(image);

        setSize(600, 300);
        setLocation(450, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String username = tfusername.getText();
            String password = tfpassword.getText();

            // Connect to MongoDB
            conn1 conn = new conn1();

            // Check if the provided username and password match a document in the "login" collection
            MongoCollection<Document> collection = conn.getDatabase().getCollection("login");
            Document query = new Document("username", username).append("password", password);
            FindIterable<Document> result = collection.find(query);

            if (result.iterator().hasNext()) {
                setVisible(false);
                new Home1();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
                setVisible(false);
            }

            // Close MongoDB connection when done
            conn.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Logins();
    }
}
