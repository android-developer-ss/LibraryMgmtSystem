/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymgmtsystem;

/**
 *
 * @author Snehal
 */
public class BookDetails_Fields {

    String book_id;
    String branch_id;
    String author;
    String authorFname;
    String authorMname;
    String authorLname;

    public void setBookDetails(BookDetails_Fields bookDetails) {
        this.book_id = bookDetails.book_id;
        this.branch_id = bookDetails.branch_id;
        this.author = bookDetails.author;
        this.authorFname = bookDetails.authorFname;
        this.authorMname = bookDetails.authorMname;
        this.authorLname = bookDetails.authorLname;
    }

    public BookDetails_Fields getBookDetails() {
        BookDetails_Fields bookDetails = new BookDetails_Fields();
        bookDetails.book_id = this.book_id;
        bookDetails.branch_id = this.branch_id;
        bookDetails.author = this.author;
        bookDetails.authorFname = this.authorFname;
        bookDetails.authorMname = this.authorMname;
        bookDetails.authorLname = this.authorLname;
        
        return bookDetails;
    }

}
