package LMS.DAO;


import LMS.Entity.Book_Loan;
import LMS.Entity.Borrower;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/27/2017.
 */
public class Book_LoanDAO extends BaseDAO implements ResultSetExtractor<List<Book_Loan>> {

    public List<Book_Loan> readAllLoanRecord()throws ClassNotFoundException, SQLException {
        List<Book_Loan> book_loans= template.query("select * from tbl_book_loans order by cardNo",this);
        return book_loans;
    }
    public List<Book_Loan> readBookLoanByPage(Integer pageNo)throws ClassNotFoundException, SQLException {
        setPageNo(pageNo);
        List<Book_Loan> book_loans=template.query("select * from tbl_book_loans order by cardNo",this);
        return book_loans;
    }

    public void checkOutBook(int bookId,int branchId,int cardNo)throws ClassNotFoundException, SQLException{
        template.update("insert into tbl_book_loans(bookId,branchId,cardNo,dateOut,dueDate) values(?,?,?,CURDATE(),CURDATE()+7)",new Object[]{bookId,branchId,cardNo});
        template.update("update tbl_book_copies set noOfCopies=noOfCopies-1 where bookId=? AND branchId=?",
                new Object[]{bookId,branchId});

    }
    public Book_Loan readBookLoanByPk(Integer bookId,Integer branchID,Integer cardNo) throws ClassNotFoundException, SQLException{
       List<Book_Loan> book_loans=  template.query("select * from tbl_book_loans where bookId=? and branchId=? and cardNo=?",
               new Object[]{bookId,branchID,cardNo},this);
        if(book_loans!=null && !book_loans.isEmpty()){
            return book_loans.get(0);
        }
        return null;
    }

    public List<Book_Loan> readBookLoanByCardNo(Integer cardNo) throws ClassNotFoundException, SQLException{
        List<Book_Loan> book_loans= template.query("select * from tbl_book_loans where cardNo=?",
                new Object[]{cardNo},this);
    return book_loans;
    }
    public void returnBook(int cardNo, int bookId, int branchId, Date dateOut) throws ClassNotFoundException, SQLException{

        template.update("delete from tbl_book_loans where cardNo=? and bookId=? and branchId=? and dateOut=?",new Object[]{cardNo,bookId,branchId,dateOut});
        template.update("update tbl_book_copies set noOfCopies=noOfcopies+1 where bookId=? and branchId=?",new Object[]{bookId,branchId});
    }

    public void updateBookLoan(Book_Loan book_loan)throws ClassNotFoundException, SQLException{

        template.update("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?"
                ,new Object[]{new java.sql.Date(book_loan.getDueDate().getTime()) ,book_loan.getBookId(),book_loan.getBranchId(),book_loan.getCardNo()});
    }
    public List<Book_Loan> readBookLoanByName(String borrowerName)throws ClassNotFoundException, SQLException {
        BorrowerDAO borrowerDAO=new BorrowerDAO();
        Borrower borrower= (Borrower) borrowerDAO.readBorrowersByName(borrowerName);
        List<Book_Loan> book_loans= template.query("select * from tbl_book_loans where cardNo=?", new Object[]{borrower.getCardNo()},this);
        return book_loans;
    }

    @Override
    public List<Book_Loan> extractData(ResultSet rs) throws SQLException {
        List<Book_Loan> book_loans=new ArrayList<Book_Loan>();
//      BookDAO bookDAO=new BookDAO();
//      BorrowerDAO borrowerDAO=new BorrowerDAO();
//      LibraryDAO libraryDAO=new LibraryDAO();

      while(rs.next()){
          Book_Loan book_loan=new Book_Loan();
//          Borrower borrower= borrowerDAO.readBorrowerByPk(rs.getInt("cardNo"));
//          Book book=bookDAO.readBookByPk(rs.getInt("bookId"));
//          Library library =libraryDAO.readLibraryByPk(rs.getInt("branchId"));
//          book_loan.setBook(book);
//          book_loan.setBorrower(borrower);
//          book_loan.setBranch(library);
          book_loan.setBookId(rs.getInt("bookId"));
          book_loan.setBranchId(rs.getInt("branchId"));
          book_loan.setCardNo(rs.getInt("cardNo"));
          book_loan.setDateIn(rs.getDate("dateIn"));
          book_loan.setDateOut(rs.getDate("dateOut"));
          book_loan.setDueDate(rs.getDate("dueDate"));
          book_loans.add(book_loan);
      }

        return book_loans;
    }


}
