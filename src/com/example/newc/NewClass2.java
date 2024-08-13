package com.example.newc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.example.homepage2.HomePage2;
import com.example.database.DatabaseConnection;
import com.example.homepage3.HomePage3;
import com.example.main.Main2;

import static com.example.main.Main2.*;

public class NewClass2 extends JFrame {

    JLabel label;
    JPanel panel;
    JPanel panel1;
    JPanel signuppanel;
    JLabel head, usr, pass, head1, pass1, usr1, signup, head2, usr2, pass2, pass3;
    JTextField usrbox, usrbox1, usrbox2, usrbox3;
    JButton but1, but2, change, change1, but3, but4;
    JPasswordField passbox, passbox1, passbox2, passbox3;

    public NewClass2() {
        setTitle("Login Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel();
        label.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\Lithika\\finalproject\\login.png").getImage().getScaledInstance(1550, 900, Image.SCALE_SMOOTH)));
        setLayout(null);
        setContentPane(label);
        setVisible(true);

        panel = new JPanel();
        panel.setBounds(565, 175, 430, 490);
        panel.setBackground(new Color(0, 4, 9, 200));
        panel.setLayout(null);
        panel.setVisible(true);
        add(panel);

        head = new JLabel("LIBRARIAN");
        panel.add(head);
        head.setBounds(160, 10, 140, 100);
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Ariel", Font.BOLD, 22));

        usr = new JLabel("Username:");
        usr.setBounds(17, 20, 200, 300);
        usr.setForeground(Color.WHITE);
        usr.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel.add(usr);

        usrbox = new JTextField();
        usrbox.setBounds(150, 163, 250, 20);
        usrbox.setBackground(Color.white);
        panel.add(usrbox);

        pass = new JLabel("Password:");
        panel.add(pass);
        pass.setBounds(17, 105, 200, 300);
        pass.setForeground(Color.WHITE);
        pass.setFont(new Font("Verdana", Font.PLAIN, 18));

        passbox = new JPasswordField();
        panel.add(passbox);
        passbox.setBounds(150, 247, 250, 20);
        passbox.setBackground(Color.WHITE);

        but1 = new JButton("Login");
        panel.add(but1);
        but1.setBounds(175, 340, 100, 40);
        but1.setBackground(new Color(115, 222, 230));

        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usrbox.getText();
                String password = new String(passbox.getPassword());
                try {
                    if (librarianLogIn(username, password)) {
                        // Credentials are correct, open Main2 frame
                        Main2 main2 = new Main2();
                        main2.setVisible(true);
                        dispose(); // Close the current frame
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        change = new JButton("If USER click here");
        panel.add(change);
        change.setBounds(127, 430, 200, 50);
        change.setBackground(new Color(0, 4, 9, 100));
        change.setBorder(null);
        change.setForeground(Color.white);
        change.addActionListener(changePage);

        panel1 = new JPanel();
        panel1.setBounds(565, 175, 430, 550);
        panel1.setBackground(new Color(0, 4, 9, 200));
        panel1.setLayout(null);
        panel1.setVisible(false);
        add(panel1);

        head1 = new JLabel("EXISTING USER");
        panel1.add(head1);
        head1.setBounds(130, 10, 175, 100);
        head1.setForeground(Color.white);
        head1.setFont(new Font("Ariel", Font.BOLD, 22));

        usr1 = new JLabel("Username:");
        usr1.setBounds(17, 20, 200, 300);
        usr1.setForeground(Color.WHITE);
        usr1.setFont(new Font("Verdana", Font.PLAIN, 18));
        panel1.add(usr1);

        usrbox1 = new JTextField();
        usrbox1.setBounds(150, 163, 250, 20);
        usrbox1.setBackground(Color.WHITE);
        panel1.add(usrbox1);

        pass1 = new JLabel("Password:");
        panel1.add(pass1);
        pass1.setBounds(17, 105, 200, 300);
        pass1.setForeground(Color.WHITE);
        pass1.setFont(new Font("Verdana", Font.PLAIN, 18));

        passbox1 = new JPasswordField();
        panel1.add(passbox1);
        passbox1.setBounds(150, 247, 250, 20);
        passbox1.setBackground(Color.WHITE);

        but2 = new JButton("Login");
        panel1.add(but2);
        but2.setBounds(175, 340, 100, 40);
        but2.setBackground(Color.cyan);

        but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the entered username and password
                String username = usrbox1.getText();
                String password = new String(passbox1.getPassword());

                // Perform user login
                try {
                    if (userLogin(username, password)) {
                        // If login successful, show a success message
                        storeLoggedInUserId(username);

                        JOptionPane.showMessageDialog(null, "Login successful.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // Open new UI or perform other actions
                                new HomePage3();
                            }
                        });
                    } else {
                        // If login failed, show an error message
                        JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    System.out.println("Error during login.");
                    ex.printStackTrace();
                }
            }
        });

        but3 = new JButton("Sign up");
        panel1.add(but3);
        but3.setBounds(175, 420, 100, 25);
        but3.setBackground(new Color(0, 4, 9, 100));
        but3.setForeground(Color.cyan);
        but3.addActionListener(signupp);

        change1 = new JButton("If LIBRARIAN click here");
        panel1.add(change1);
        change1.setBounds(120, 460, 200, 50);
        change1.setBackground(new Color(0, 4, 9, 100));
        change1.setBorder(null);
        change1.setForeground(Color.WHITE);
        change1.addActionListener(reverse);

        signuppanel = new JPanel();
        signuppanel.setBounds(555, 175, 460, 550);
        signuppanel.setBackground(new Color(0, 4, 9, 200));
        signuppanel.setLayout(null);
        signuppanel.setVisible(false);
        add(signuppanel);

        JButton but5 = new JButton("<--");
        signuppanel.add(but5);
        but5.setBounds(10, 50, 28, 20);
        but5.setBorder(null);
        but5.setBackground(new Color(0, 4, 9, 100));
        but5.setForeground(Color.WHITE);
        but5.addActionListener(backbutt);

        head2 = new JLabel("NEW USER SIGN UP");
        signuppanel.add(head2);
        head2.setBounds(130, 10, 200, 100);
        head2.setForeground(Color.white);
        head2.setFont(new Font("Ariel", Font.BOLD, 20));

        usr2 = new JLabel("New Username:");
        usr2.setBounds(17, 20, 200, 300);
        usr2.setForeground(Color.WHITE);
        usr2.setFont(new Font("Verdana", Font.PLAIN, 15));
        signuppanel.add(usr2);

        usrbox2 = new JTextField();
        usrbox2.setBounds(180, 160, 250, 20);
        usrbox2.setBackground(Color.white);
        signuppanel.add(usrbox2);

        pass2 = new JLabel("Password:");
        signuppanel.add(pass2);
        pass2.setBounds(17, 80, 200, 300);
        pass2.setForeground(Color.WHITE);
        pass2.setFont(new Font("Verdana", Font.PLAIN, 15));

        passbox2 = new JPasswordField();
        signuppanel.add(passbox2);
        passbox2.setBounds(180, 220, 250, 20);
        passbox2.setBackground(Color.WHITE);

        pass3 = new JLabel("Confirm Password:");
        signuppanel.add(pass3);
        pass3.setBounds(17, 140, 200, 300);
        pass3.setForeground(Color.WHITE);
        pass3.setFont(new Font("Verdana", Font.PLAIN, 15));

        passbox3 = new JPasswordField();
        signuppanel.add(passbox3);
        passbox3.setBounds(180, 280, 250, 20);
        passbox3.setBackground(Color.WHITE);

        but4 = new JButton("Sign up");
        signuppanel.add(but4);
        but4.setBounds(175, 340, 100, 40);
        but4.setBackground(Color.cyan);

        but4.addActionListener(signupdb);

        setVisible(true);
    }

    private void storeLoggedInUserId(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_id.txt"))) {
                        writer.write(String.valueOf(userId));
                    } catch (IOException e) {
                        System.err.println("Error writing user ID to file: " + e.getMessage());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying user ID: " + e.getMessage());
        }
    }

    // Placeholder method for librarianLogIn
    private boolean librarianLogIn(String username, String password) throws SQLException {
        // Implement the actual login logic here
        return true; // Return true if login is successful, false otherwise
    }

    // Placeholder method for userLogin
    private boolean userLogin(String username, String password) throws SQLException {
        // Implement the actual login logic here
        return true; // Return true if login is successful, false otherwise
    }

    private ActionListener changePage = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setVisible(false);
            panel1.setVisible(true);
        }
    };

    private ActionListener reverse = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setVisible(true);
            panel1.setVisible(false);
        }
    };

    private ActionListener signupp = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel1.setVisible(false);
            signuppanel.setVisible(true);
        }
    };

    private ActionListener backbutt = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            signuppanel.setVisible(false);
            panel1.setVisible(true);
        }
    };

    private ActionListener signupdb = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newUsername = usrbox2.getText();
            String password = new String(passbox2.getPassword());
            String confirmPassword = new String(passbox3.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection connection = DatabaseConnection.getConnection()) {
                String insertUserSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSQL)) {
                    insertUserStmt.setString(1, newUsername);
                    insertUserStmt.setString(2, password);
                    insertUserStmt.executeUpdate();
                }

                JOptionPane.showMessageDialog(null, "Sign up successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error during sign up.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewClass2::new);
    }
}
