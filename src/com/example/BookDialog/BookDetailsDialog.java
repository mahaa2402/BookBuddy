package com.example.BookDialog;

//import
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import com.example.bookclass.BookClass.Book;
import com.example.database.DatabaseConnection;
import com.example.homepage2.HomePage2;
import com.example.homepage3.HomePage3;
import com.example.subsplan.SubscriptionPlanPage;

public class BookDetailsDialog extends JFrame {
    private JPopupMenu optionMenu;

    public BookDetailsDialog(HomePage2 homePage2, Book book) {
    }

    public BookDetailsDialog(HomePage3 homePage3, Book book) {
    }


    private JPanel createDetailsPanel(Book book) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        detailsPanel.setBackground(Color.decode("#F0F3ED"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        addDetail(detailsPanel, gbc, "ISBN:", book.getIsbn(), 0);
        addDetail(detailsPanel, gbc, "Edition:", String.valueOf(book.getBookEdition()), 1);
        addDetail(detailsPanel, gbc, "Genre ID:", String.valueOf(book.getGenreId()), 2);
        addDetail(detailsPanel, gbc, "Author Name:", book.getAuthorname(), 3);

        return detailsPanel;
    }

    private void addDetail(JPanel panel, GridBagConstraints gbc, String label, String value, int row) {
        JLabel detailLabel = new JLabel(label);
        detailLabel.setFont(new Font("Calibri", Font.BOLD, 16));
        detailLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(detailLabel, gbc);

        JLabel detailValue = new JLabel(value);
        detailValue.setFont(new Font("Calibri", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(detailValue, gbc);
    }

    private JPanel createButtonPanel(Book book) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.decode("#F0F3ED"));

        JButton readButton = new JButton("Read");
        JButton subscribeButton = new JButton("Subscribe");
        JButton optionButton = new JButton("...");

        configureButton(readButton);
        configureButton(subscribeButton);
        configureButton(optionButton);

        subscribeButton.addActionListener(e -> {
            new SubscriptionPlanPage();
        });

        optionMenu = createOptionMenu(book);

        optionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                optionMenu.show(optionButton, 0, optionButton.getHeight());
            }
        });

        buttonPanel.add(readButton);
        buttonPanel.add(subscribeButton);
        buttonPanel.add(optionButton);

        return buttonPanel;
    }

    private void configureButton(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Calibri", Font.BOLD, 16));
        button.setBackground(new Color(0, 128, 255));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));
    }

    private JPopupMenu createOptionMenu(Book book) {
        optionMenu = new JPopupMenu();
        optionMenu.setPreferredSize(new Dimension(200, 150));
        optionMenu.setBackground(Color.LIGHT_GRAY);
        optionMenu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JMenuItem addToList = new JMenuItem("Add to Bookshelf");
        JMenuItem alreadyRead = new JMenuItem("Already Read");
        JMenuItem impressions = new JMenuItem("Impressions");
        JMenuItem reviews = new JMenuItem("Reviews");

        addToList.addActionListener(e -> addBookToBookshelf(book));

        optionMenu.add(addToList);
        optionMenu.add(alreadyRead);
        optionMenu.add(impressions);
        optionMenu.add(reviews);

        return optionMenu;
    }

    private void addBookToBookshelf(Book book) {
        // Ask the user for their user ID
        //String userIdStr = JOptionPane.showInputDialog(this, "Enter your user ID:");

        // Validate the user ID
        //if (userIdStr == null || userIdStr.trim().isEmpty()) {
         //   JOptionPane.showMessageDialog(this, "User ID not provided.", "Error", JOptionPane.ERROR_MESSAGE);
         //   return;
        //}

        try {
            int userId = getUserId();

            // Check if the user ID exists in the users table
            if (!isUserExist(userId)) {
                JOptionPane.showMessageDialog(this, "User does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the book to the user's bookshelf
            insertBookToBookshelf(userId, book.getBookId());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid user ID format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public int getUserId() {
        int user_id = -1;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT user_id FROM logged_in_user";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        user_id = rs.getInt("user_id");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving user ID: " + e.getMessage());
            e.printStackTrace();
        }
        return user_id;
    }

    private void insertBookToBookshelf(int userId, int bookId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO bookshelf (user_id, book_id, added_date) VALUES (?, ?, SYSDATE)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, bookId);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Book added to bookshelf!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding book to bookshelf: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isUserExist(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
