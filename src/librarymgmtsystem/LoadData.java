/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymgmtsystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static librarymgmtsystem.LibraryMgmtSystem.conn;

/**
 *
 * @author Snehal
 */
public class LoadData {

    public static int book_id_length = 10;
    public static int author_length = 80;
    public static int title_length = 80;
    public static int fname_length = 30;
    public static int mname_length = 1;
    public static int lname_length = 30;
    public static int addr_length = 80;
    public static int phone_length = 14;
    public static int branch_length = 30;
    public static int card_no_length = 10;

    public static String checkApostrophe(String inputString) {
        String str;
        String begin;
        String end;

        str = inputString;
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == '\'') {
                begin = inputString.substring(0, i);
                end = inputString.substring(i + 1);
                str = begin + "''" + end;
                break;
            }
        }

        return str;
    }

    /**
     * *************************************************************************
     * CREATE TABLE BOOK_COPIES.
     *************************************************************************
     */
    public static void createBookCopies() {
        try {
            // Create a connection to the local MySQL server, with the "company" database selected.
            //        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
            // Create a connection to the local MySQL server, with the NO database selected.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Snehal89");

            // Create a SQL statement object and execute the query.
            Statement stmt = conn.createStatement();

            //Create Schema; 
            stmt.execute("CREATE DATABASE IF NOT EXISTS Library_schema ;");

            // Set the current database, if not already set in the getConnection
            // Execute a SQL statement
            stmt.execute("use Library_schema;");

            //Check if the table already exists, if yes drop it and then create
            //a new table and then reload the whole table.
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'BOOK_COPIES';");
            if (rs != null) {
                stmt.execute("DROP TABLE BOOK_COPIES");
            }

            try ( //READ from file
                    BufferedReader reader = new BufferedReader(new FileReader("book_copies.csv"))) {
                String line;
                String split_line[]; // = new String[3];
                String book_id;
                int branch_id;
                int no_of_copies;
                line = reader.readLine();// to exclude the heading line
                split_line = line.split("\t");
                stmt.execute("CREATE TABLE BOOK_COPIES ( "
                        + split_line[0] + " varchar(" + book_id_length + ") not null,"
                        + split_line[1] + " int not null, "
                        + split_line[2] + " int) ");

                while ((line = reader.readLine()) != null) {
                    split_line = line.split("\t");
                    book_id = split_line[0];
                    branch_id = Integer.parseInt(split_line[1]);
                    no_of_copies = Integer.parseInt(split_line[2]);
                    stmt.execute("INSERT INTO BOOK_COPIES VALUES ('"
                            + book_id + "','" + branch_id + "','" + no_of_copies + "');");
                }
            }
            conn.close();
            System.out.println("BOOK_COPIES table loaded Successfully!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *************************************************************************
     * CREATE TABLE BOOKS_AUTHORS.
     *************************************************************************
     */
    public static void createBooksAuthors() {
        int count = 0;
        try {
            // Create a connection to the local MySQL server, with the "company" database selected.
            //        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
            // Create a connection to the local MySQL server, with the NO database selected.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Snehal89");

            // Create a SQL statement object and execute the query.
            Statement stmt = conn.createStatement();

            //Create Schema; 
            stmt.execute("CREATE DATABASE IF NOT EXISTS Library_schema ;");

            // Set the current database, if not already set in the getConnection
            // Execute a SQL statement
            stmt.execute("use Library_schema;");

            //Check if the table already exists, if yes drop it and then create
            //a new table and then reload the whole table.
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'BOOKS_AUTHORS';");
            if (rs.next()) {
                stmt.execute("DROP TABLE BOOKS_AUTHORS");
            }
            rs = stmt.executeQuery("SHOW TABLES LIKE 'BOOKS';");
            if (rs.next()) {
                stmt.execute("DROP TABLE BOOKS");
            }

            try ( //READ from file
                    BufferedReader reader = new BufferedReader(new FileReader("books_authors.csv"))) {
                String line;
                String split_line[]; // = new String[3];
                String split_authors[];
                String split_authorName[];
                String book_id;
                String author_name;
                String title;
                line = reader.readLine();// to exclude the heading line
                split_line = line.split("\t");
                stmt.execute("CREATE TABLE BOOKS_AUTHORS ( "
                        + split_line[0] + " varchar(" + book_id_length + ") not null,"
                        + split_line[1] + " char(" + author_length + "),"
                        + "fname" + " varchar(" + fname_length + "), "
                        + "mname" + " varchar(" + mname_length + "), "
                        + "lname" + " varchar(" + lname_length + ") "
                        + ") ");

                stmt.execute("CREATE TABLE BOOKS ( "
                        + split_line[0] + " varchar(" + book_id_length + ") not null,"
                        + split_line[2] + " char(" + title_length + ")) ");

                while ((line = reader.readLine()) != null) {
                    line = checkApostrophe(line);
                    split_line = line.split("\t");
                    book_id = split_line[0];
                    author_name = split_line[1];
                    title = split_line[2];

                    //Split author
                    String fname = "", mname = "", lname = "";
                    split_authors = author_name.split(",");
                    split_authorName = split_authors[0].split(" ");
                    if (split_authorName.length == 2) {
                        fname = split_authorName[0];
                        lname = split_authorName[1];
                    } else if (split_authorName.length == 3) {
                        fname = split_authorName[0];
                        mname = split_authorName[1].charAt(0) + "";
                        lname = split_authorName[2];
                    }

                    stmt.execute("INSERT INTO BOOKS_AUTHORS VALUES ('"
                            + book_id + "','"
                            + author_name + "','"
                            + fname + "','"
                            + mname + "','"
                            + lname
                            + "');");
                    stmt.execute("INSERT INTO BOOKS VALUES ('"
                            + book_id + "','"
                            + title + "');");
                }
            }
            conn.close();
            System.out.println("BOOKS_AUTHORS table loaded Successfully!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *************************************************************************
     * CREATE TABLE BORROWER.
     *************************************************************************
     */
    public static void createBorrower() {
        int count = 0;
        try {
            // Create a connection to the local MySQL server, with the "company" database selected.
            //        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
            // Create a connection to the local MySQL server, with the NO database selected.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Snehal89");

            // Create a SQL statement object and execute the query.
            Statement stmt = conn.createStatement();

            //Create Schema; 
            stmt.execute("CREATE DATABASE IF NOT EXISTS Library_schema ;");

            // Set the current database, if not already set in the getConnection
            // Execute a SQL statement
            stmt.execute("use Library_schema;");

            //Check if the table already exists, if yes drop it and then create
            //a new table and then reload the whole table.
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'BORROWERS';");
            if (rs.next()) {
                stmt.execute("DROP TABLE BORROWERS");
            }

            try ( //READ from file
                    BufferedReader reader = new BufferedReader(new FileReader("borrowers.csv"))) {
                String line;
                String split_line[]; // = new String[3];
                String split_author[];
                int card_no;
                String fname;
                String lname;
                String address;
                String addr, city, state;
                String phone;

                line = reader.readLine();// to exclude the heading line
                split_line = line.split("\t");
                stmt.execute("CREATE TABLE BORROWERS ( "
                        + split_line[0] + " int not null, "
                        + split_line[1] + " varchar(" + fname_length + "),"
                        + split_line[2] + " varchar(" + lname_length + "),"
                        + split_line[3] + " varchar(" + addr_length + "),"
                        + split_line[6] + " varchar(" + phone_length + ")) ");

                while ((line = reader.readLine()) != null) {
                    line = checkApostrophe(line);
                    split_line = line.split("\t");
                    card_no = Integer.parseInt(split_line[0]);
                    fname = split_line[1];
                    lname = split_line[2];
                    addr = split_line[3];
                    city = split_line[4];
                    state = split_line[5];
                    address = addr + ", " + city + ", " + state;
                    phone = split_line[6];

                    stmt.execute("INSERT INTO BORROWERS VALUES ('"
                            + card_no + "','"
                            + fname + "','"
                            + lname + "','"
                            + address + "','"
                            + phone + "');");
                }
            }
            conn.close();
            System.out.println("BORROWERS table loaded Successfully!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *************************************************************************
     * CREATE TABLE LIBRARY BRANCH.
     *************************************************************************
     */
    public static void createLibraryBranch() {
        int count = 0;
        try {
            // Create a connection to the local MySQL server, with the "company" database selected.
            //        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
            // Create a connection to the local MySQL server, with the NO database selected.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Snehal89");

            // Create a SQL statement object and execute the query.
            Statement stmt = conn.createStatement();

            //Create Schema; 
            stmt.execute("CREATE DATABASE IF NOT EXISTS Library_schema ;");

            // Set the current database, if not already set in the getConnection
            // Execute a SQL statement
            stmt.execute("use Library_schema;");

            //Check if the table already exists, if yes drop it and then create
            //a new table and then reload the whole table.
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'LIBRARY_BRANCH';");
            if (rs.next()) {
                stmt.execute("DROP TABLE LIBRARY_BRANCH");
            }

            try ( //READ from file
                    BufferedReader reader = new BufferedReader(new FileReader("library_branch.csv"))) {
                String line;
                String split_line[]; // = new String[3];
                String split_author[];
                int branch_id;
                String branch_name;
                String address;

                line = reader.readLine();// to exclude the heading line
                split_line = line.split("\t");
                stmt.execute("CREATE TABLE LIBRARY_BRANCH ( "
                        + split_line[0] + " int not null, "
                        + split_line[1] + " varchar(" + branch_length + "),"
                        + split_line[2] + " varchar(" + addr_length + ")) ");

                while ((line = reader.readLine()) != null) {
                    line = checkApostrophe(line);
                    split_line = line.split("\t");
                    branch_id = Integer.parseInt(split_line[0]);
                    branch_name = split_line[1];
                    address = split_line[2];

                    stmt.execute("INSERT INTO LIBRARY_BRANCH VALUES ('"
                            + branch_id + "','"
                            + branch_name + "','"
                            + address + "');");
                }
            }
            conn.close();
            System.out.println("LIBRARY_BRANCH table loaded Successfully!!");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoadData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *************************************************************************
     * CREATE TABLE BOOK_LOAN.
     *************************************************************************
     */
    public static void createBookLoans() {
        int count = 0;
        try {
            // Create a connection to the local MySQL server, with the "company" database selected.
            //        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "mypassword");
            // Create a connection to the local MySQL server, with the NO database selected.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Snehal89");

            // Create a SQL statement object and execute the query.
            Statement stmt = conn.createStatement();

            //Create Schema; 
            stmt.execute("CREATE DATABASE IF NOT EXISTS Library_schema ;");

            // Set the current database, if not already set in the getConnection
            // Execute a SQL statement
            stmt.execute("use Library_schema;");

            //Check if the table already exists, if yes drop it and then create
            //a new table and then reload the whole table.
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'BOOK_LOANS';");
            if (rs.next()) {
                stmt.execute("DROP TABLE BOOK_LOANS");
            }

            stmt.execute("CREATE TABLE BOOK_LOANS ( "
                    + "loan_id" + " int not null, "
                    + "book_id  " + " varchar(" + book_id_length + ") not null,"
                    + "branch_id" + " int, "
                    + "card_no" + " varchar(" + card_no_length + "),"
                    + "date_out" + " DATE,"
                    + "due_date" + " DATE,"
                    + "date_in"  + " DATE" 
                    + ") ");
        } catch (SQLException ex) {
            System.out.println("Error in connection: " + ex.getMessage());
        }

    }

    /**
     * *************************************************************************
     * MAIN FUNCTION TO CREATE ALL TABLES.
     *************************************************************************
     */
    public static void create_all_tables() {
//        createBookCopies();
//        createBooksAuthors();
//        createBorrower();
//        createLibraryBranch();
        createBookLoans();
    }

}
