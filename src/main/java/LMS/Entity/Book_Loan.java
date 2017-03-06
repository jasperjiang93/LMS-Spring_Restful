package LMS.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kingj on 2/23/2017.
 */
public class Book_Loan implements Serializable {
    private static final long serialVersionUID = -449465824999837605L;

    private int bookId;
    private int branchId;
    private int cardNo;
    private Book book;
    private Library branch;
    private Borrower borrower;
    private Date dateOut;
    private Date dueDate;
    private Date dateIn;

    public Library getBranch() {
        return branch;
    }

    public void setBranch(Library branch) {
        this.branch = branch;
    }

    public Book getBook() {

        return book;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public Borrower getBorrower() {
        return borrower;
    }

    public void setBorrower(Borrower borrower) {
        this.borrower = borrower;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book_Loan book_loan = (Book_Loan) o;

        if (bookId != book_loan.bookId) return false;
        if (branchId != book_loan.branchId) return false;
        if (cardNo != book_loan.cardNo) return false;
        if (dateOut != null ? !dateOut.equals(book_loan.dateOut) : book_loan.dateOut != null) return false;
        if (dueDate != null ? !dueDate.equals(book_loan.dueDate) : book_loan.dueDate != null) return false;
        return dateIn != null ? dateIn.equals(book_loan.dateIn) : book_loan.dateIn == null;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + branchId;
        result = 31 * result + cardNo;
        result = 31 * result + (dateOut != null ? dateOut.hashCode() : 0);
        result = 31 * result + (dueDate != null ? dueDate.hashCode() : 0);
        result = 31 * result + (dateIn != null ? dateIn.hashCode() : 0);
        return result;
    }
}
