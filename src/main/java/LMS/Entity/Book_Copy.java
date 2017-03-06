package LMS.Entity;

import java.io.Serializable;

/**
 * Created by kingj on 2/23/2017.
 */
public class Book_Copy implements Serializable{
    private static final long serialVersionUID = 6527612013233820356L;
    private int bookId;
    private int branchId;
    private int noOfCopies;

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

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

}
