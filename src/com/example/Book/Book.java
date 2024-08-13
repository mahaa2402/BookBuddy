package com.example.Book;
import com.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private static Connection conn;
    private String bookName;
    private String isbn;
    private int bookEdition;
    private String genreName;
    private String authorName;
    private String publisherName;
    private int genreId;  // genreId field

    // Add a constructor to initialize the genreId field if necessary
    public Book(String bookName, String isbn, int bookEdition, int genre) throws SQLException {
        this.conn = DatabaseConnection.getConnection();

        if (this.conn == null) {
            System.err.println("Failed to establish a connection to the database.");
            return;
        }

        this.bookName = bookName;
        this.isbn = isbn;
        this.bookEdition = bookEdition;
        this.genreId = genre;
    }





    // Getter methods
    public String getBookName() {
        return bookName;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getBookEdition() {
        return bookEdition;
    }

    public String getGenreName() {
        return genreName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }


    public static List<Book> getBooksByGenre(int genre) {
        List<Book> books = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM book WHERE genre_id = ?");
        ) {
            stmt.setInt(1, genre);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String bookName = rs.getString("book_name");
                    String isbn = rs.getString("isbn");
                    int bookEdition = rs.getInt("book_edition");
                    //String authorName = rs.getString("author_name");
                   // String publisherName = rs.getString("publisher_name");
                    books.add(new Book(bookName, isbn, bookEdition, genre));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}




