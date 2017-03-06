package LMS.DAO;

import LMS.Entity.Book_Copy;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/28/2017.
 */
public class Book_CopyDAO extends BaseDAO implements ResultSetExtractor<List<Book_Copy>> {

    public List<Book_Copy> readAllBookCopy() throws ClassNotFoundException, SQLException{
        List<Book_Copy>book_copies= template.query("select * from tbl_book_copies order by bookId", this);
        return book_copies;
    }

    public Book_Copy readBookCopyByPk(int bookId,int branchId) throws ClassNotFoundException, SQLException{
        List<Book_Copy>book_copies= template.query("select * from tbl_book_copies where bookId=? and branchId=?",
                new Object[]{bookId,branchId},this);
        if(book_copies!=null && !book_copies.isEmpty()){
            return book_copies.get(0);
        }
        return null;
    }

    public void checkOutBook(int bookId,int branchId,int cardNo)throws ClassNotFoundException, SQLException{
        template.update("insert into tbl_book_loans(bookId,branchId,cardNo,dateOut,dueDate) values(?,?,?,NOW(),NOW()+7)",new Object[]{bookId,branchId,cardNo});
        template.update("update tbl_book_copies set noOfCopies=noOfCopies-1 where bookId=? AND branchId=?",
                new Object[]{bookId,branchId});
    }


    public void increase1(int bookId,int branchId)throws ClassNotFoundException, SQLException{
        template.update("update tbl_book_copies set noOfCopies=(noOfCopies+1) where bookId=? and branchId=? ",new Object[]{bookId,branchId});
    }
    public void updateCopy(Book_Copy book_copy)throws ClassNotFoundException, SQLException{
        template.update("update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?",
                new Object[]{book_copy.getNoOfCopies(),book_copy.getBookId(),book_copy.getBranchId()});
    }
    public void insertCopy(Book_Copy book_copy) throws SQLException, ClassNotFoundException {
        template.update("Insert into tbl_book_copies(bookId,branchId,noOfCopies) values(?,?,?)",
                new Object[] {book_copy.getBookId(),book_copy.getBranchId(),book_copy.getNoOfCopies()});
    }

    @Override
    public List<Book_Copy> extractData(ResultSet rs) throws SQLException{
        List<Book_Copy> book_copies=new ArrayList<Book_Copy>();

        int noOfBook;
        int bookId;
        int branchID;
        while(rs.next())
        {
           Book_Copy book_copy=new Book_Copy();
            bookId= rs.getInt("bookId");
            branchID=rs.getInt("branchId");
            noOfBook=rs.getInt("noOfCopies");
            book_copy.setNoOfCopies(noOfBook);
            book_copy.setBranchId(branchID);
            book_copy.setBookId(bookId);
            book_copies.add(book_copy);
        }
        return book_copies;
    }
}
