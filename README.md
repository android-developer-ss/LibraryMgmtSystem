# LibraryMgmtSystem
Library management is an application where all the information related to books, branches, checked in books, checked out books, etc. is stored in normalized form.




LIBRARY MANAGEMENT SYSTEM
Developed by: Snehal Sutar
Subject: Database Design
 (Prof. Chris Davis)













Abstract:
Library management is an application where all the information related to books, branches, checked in books, checked out books, etc. is stored in normalized form. User friendly GUI is provided to interact with the application. Validations are done on all the fields. Wherever possible tabular format is used to display the data and user is allowed to click on the respective rows to make further manipulations to the data- this is done to keep the interaction with the application user friendly. 

Implementation:
Library management implements 5 functionalities as follows: 
1.	Search Books: 
a.	Books are searched based on Book Id, Book Title or Author.
b.	 User can enter either full id, or partial id the search will return proper result. 
c.	If only book id is entered then search result will be based just upon book id. 
d.	If book title is entered partially it will return all the book which has that search substring in its title.
e.	If both book title and book author is entered then entries that have both of partial book title and book author are returned in the search results. 
f.	There is an addition row displayed Available copies which displays how many copies are still in the library i.e. those which are not checked out. 

2.	Check Out book: 
a.	Once you enter all the required fields of book id, branch id, borrower card no – all the fields are validated if they are actually existing.
b.	If the validation is not done and book id, branch id or borrower card no is not present in their respective tables then the query will fail. So the necessary validation is done in the project to run the queries query smoothly.
c.	Once the book is checked out successfully then all the books which are checked out by that user is displayed in the table.
d.	Validations are done to check if user has already checked out 3 books from the library. If he has already checked out then a message is displayed saying he has already checked out 3 books and he is not allowed to further check out the book. 
e.	Available number of books are checked. If the total number of available copies at particular branch minus the total number of books checked out from same combination of book id branch is greater than 0 only then the user is allowed to check in the book.
f.	If there are unpaid fines for the user, he is not allowed to check out book and a message is displayed saying please pay your fine. Once all the fines against him are cleared only then he is allowed to check out next book.


3.	Check in Book: 
a.	Gives option to search all the tuples based on either book id, card no of the user or borrower’s first name or last name. 
b.	Once the admin has entered whatever he knows about the book to be checked in, then search is performed based on the selected criteria and then the results are displayed in the table on the screen.
c.	Once the user clicks on the row for which book he has to check –in the book, the check in button is activated and he is allowed to check in. 
d.	Book loans table is updated – todays date is updated in the date in field.
4.	Add Borrower
a.	New card number is issued for a new card by searching for the maximum card number issued previously and then that number is incremented by 1 and displayed in the card number field.
b.	Borrower is added by entering first name, last name, address and phone number. 
c.	First name, last name and address are all mandatory fields.
d.	Before updating the borrowers table, borrowers table is checked if the user is already issued a card by checking if an entry already exists with same name and address. If it does exist then message is displayed saying this is the card number which that particular user is already having. 
e.	Once all the validations are done and are passed successfully, user is added to the database.

5.	Fines updating/payment
a.	Update button is provided to update the full fines table (similar to batch processing). Once the button is clicked, fines will be generated for those who have not returned the book yet and their due is already passed. Also for them who have returned the book but their due date is already passed.
b.	Fines are displayed in 2 tables – First table displays fines based on load id, second table displays fine based on card number i.e. borrower card number.
c.	Options are provided to display the previously paid fines. If user selects to display just the unpaid fines he can do so by selecting the checkbox corresponding to unpaid fines.
d.	Once all the fines are listed, user can pay fines by clicking on the pay fine button. 
e.	Validation is done to check if the user has returned the book. If he has checked in the book only then allow the user to pay the fine. 








Table Schema used:


 






Extra features implemented:
1.	Partial fine payment: All the fines are displayed based on the loan id in one table and total fine for that card number is displayed in another table. User is allowed to pay the fine part by part. For each loan id once he pays the fine, all fines are cleared against him.
2.	Existing Borrower card number: If the user is already existing, which user is already existing – the card number for the same is returned for user’s reference. 
3.	Save search results: Save search results into text file on book search. 
4.	Add book copies: Once the books are searched, then add books to the number of copies. 
5.	Login: Start using app only when user id and password matches to user id : svs130130 and password “UTD”.


Development platform used:
NetBeans IDE 7.4 along with MySQL is used to develop the Library management application. 

