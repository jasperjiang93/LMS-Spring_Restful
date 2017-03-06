package LMS.Service;


import LMS.DAO.*;
import LMS.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LibrarianService {
    @Autowired
    AuthorDAO adao;
    @Autowired
    BookDAO bdao;
    @Autowired
    GenreDAO genreDAO;
    @Autowired
    AuthorDAO authorDAO;
    @Autowired
    PublisherDAO publisherDao;
    @Autowired
    Book_CopyDAO book_copyDAO;
    @Autowired
    Book_LoanDAO book_loanDAO;
    @Autowired
    BorrowerDAO borrowerDAO;
    @Autowired
    LibraryDAO libraryDAO;

    @Transactional
    public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {return bdao.readAllBooks();}

    @Transactional
    public List<Book_Loan> readAllBookLoan(Integer pageNo, String borrowerName) throws SQLException, ClassNotFoundException {
        List<Book_Loan> book_loans;
        Book book;
        Borrower borrower;
        Library branch ;
        if(pageNo!=null)
            book_loans= book_loanDAO.readBookLoanByPage(pageNo);
        else
            book_loans= book_loanDAO.readBookLoanByName(borrowerName);

        for(Book_Loan book_loan:book_loans)
        {
            book=bdao.readBookByPk(book_loan.getBookId());
            borrower=borrowerDAO.readBorrowerByPk(book_loan.getCardNo());
            branch=libraryDAO.readLibraryByPk(book_loan.getBranchId());
            book_loan.setBook(book);
            book_loan.setBorrower(borrower);
            book_loan.setBranch(branch);
        }
        return book_loans;
    }
    @Transactional
    public List<Library> getAllLibraries() throws SQLException, ClassNotFoundException {
        List<Library> libraries= libraryDAO.readAllLibaray();
        List<Book> books;
        for(Library a:libraries)
        {
            books=bdao.readBooksByBranch(a.getLibraryId());
            a.setBooks(books);
        }
        return libraries;
    }
    @Transactional
    public List<Book_Copy> getAllBookCopies() throws SQLException, ClassNotFoundException {return book_copyDAO.readAllBookCopy();}
    @Transactional
    public Book getBookByPk(Integer bookId) throws SQLException, ClassNotFoundException {return bdao.readBookByPk(bookId);}
    @Transactional
    public Library getLibraryByPk(Integer libraryId) throws SQLException, ClassNotFoundException {return libraryDAO.readLibraryByPk(libraryId);}


//    ConnectionUtil util = new ConnectionUtil();
//
//    public void addLibrary(Library library) throws SQLException {
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            LibraryDAO ldao = new LibraryDAO(conn);
//            ldao.addLibrary(library);
//            conn.commit();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            conn.rollback();
//        } finally{
//            if(conn!=null)
//                conn.close();
//        }
//    }
//
//    public void updateLibrary(Library library) throws SQLException{
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            LibraryDAO ldao = new LibraryDAO(conn);
//            ldao.updateLibaray(library);
//            conn.commit();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            conn.rollback();
//        } finally{
//            if(conn!=null)
//                conn.close();
//        }
//    }
//
//    public void deleteLibrary(Library library) throws SQLException{
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            LibraryDAO ldao = new LibraryDAO(conn);
//            ldao.deleteLibrary(library);
//            conn.commit();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//            conn.rollback();
//        } finally{
//            if(conn!=null)
//                conn.close();
//        }
//    }
//
//    public List<Library> readLibraries() throws SQLException{
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            LibraryDAO ldao = new LibraryDAO(conn);
//            return ldao.readAllLibaray();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally{
//            if(conn!=null)
//                conn.close();
//        }
//        return null;
//    }
//
//    public List<Book> readBooks(Integer pageNo, String bookName,String publisherName,String genre,String authorName) throws SQLException{
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            BookDAO bdao = new BookDAO(conn);
//            if(pageNo!=null)
//                return bdao.readBooksByPage(pageNo);
//            else
//            return bdao.readAllBooksByCondition(bookName,genre,authorName,publisherName);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally{
//            if(conn!=null)
//                conn.close();
//        }
//        return null;
//    }
//
//
//    public void updateNumberOfCopies(Book_Copy book_copy)throws SQLException{
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            LibraryDAO libraryDAO=new LibraryDAO(conn);
//            libraryDAO.updateNumberOfCopies(book_copy);
//            conn.commit();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            conn.rollback();
//        }
//        finally {
//            conn.close();
//        }
//    }
//    public void insertNumberOfCopies(Book_Copy book_copy)throws SQLException{
//        Connection conn = null;
//        try {
//            conn = util.getConnection();
//            LibraryDAO libraryDAO=new LibraryDAO(conn);
//            libraryDAO.insertNumberOfCopies(book_copy);
//            conn.commit();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            conn.rollback();
//        }
//        finally {
//            conn.close();
//        }
//    }


}
