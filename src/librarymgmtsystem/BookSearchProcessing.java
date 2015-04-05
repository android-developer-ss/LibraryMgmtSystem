/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package librarymgmtsystem;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static librarymgmtsystem.LibraryMgmtSystem.conn;

/**
 *
 * @author Snehal
 */
public class BookSearchProcessing {
    
    public void searchBooks(BookDetails_Fields bookdetails){
        try {
            // Create a connection to the local MySQL server, with the "company" database selected.
            //        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
            // Create a connection to the local MySQL server, with the NO database selected.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Snehal89");

            // Create a SQL statement object and execute the query.
            Statement stmt = conn.createStatement();

            // Set the current database, if not already set in the getConnection
            // Execute a SQL statement
            stmt.execute("use company;");

            // Execute a SQL query using SQL as a String object
//            ResultSet rs = stmt.executeQuery("SELECT ssn, fname, lname, dno FROM employee;");
            String query = "SELECT * FROM books where books_id=;";
            query = query + bookdetails.book_id;
            ResultSet rs = stmt.executeQuery(query);

            // Iterate through the result set using ResultSet class's next() method
            while (rs.next()) {
                // Keep track of the line/tuple count
//                linect++;
//                // Populate field variables
//
//                ssn = rs.getString("ssn");
//                firstName = rs.getString("fname");
//                lastName = rs.getString("lname");
//                dno = rs.getInt("dno");
//
//                // Do something with the data
//                System.out.print(linect + ".\t");
//                System.out.print(ssn + "\t");
//                System.out.print(firstName + "\t");
//                System.out.print(lastName + "\t");
//                System.out.print(dno + "\t");
//                System.out.println();
            } // End while(rs.next())

            // Always close the recordset and connection.
            rs.close();
            conn.close();
            System.out.println("Success!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        }
    }
    
}
