package LMS.MVC;

import LMS.DAO.*;
import LMS.Entity.*;
import LMS.Service.BorrowerService;
import LMS.Service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 3/3/17.
 */
@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    @Autowired
    BorrowerService borrowerService;
    @Autowired
    BorrowerDAO borrowerDAO;
    @Autowired
    Book_LoanDAO book_loanDAO;
    @Autowired
    BookDAO bookDAO;
    @Autowired
    GenreDAO genreDAO;
    @Autowired
    AuthorDAO authorDAO;
    @Autowired
    PublisherDAO publisherDAO;
    @Autowired
    LibraryDAO libraryDAO;

    @RequestMapping(value="/iniBorrower", method = RequestMethod.GET, produces = "application/json")
    public Borrower iniBorrower() {return new Borrower();}
    @RequestMapping(value="/iniLibrary", method = RequestMethod.GET, produces = "application/json")
    public Library iniLibrary() {return new Library();}

    @RequestMapping(value="/getBorrowerByPk/{id}", method = RequestMethod.GET, produces = "application/json")
    public Borrower getBorrowerByPk(@PathVariable int id) throws SQLException, ClassNotFoundException {
        return borrowerDAO.readBorrowerByPk(id);}
    @RequestMapping(value="/getBooksByBranchId/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<Book> getBooksByBranchId(@PathVariable int id) throws SQLException, ClassNotFoundException {
       List<Book> books= bookDAO.readBooksByBranch(id);
        List<Genre> genres;
        List<Author> authors;
        Publisher publisher;
        for(Book a:books)
        {
            genres=genreDAO.getGenresByBookId(a.getBookId());
            authors=authorDAO.getAuthorsByBookId(a.getBookId());
            publisher=publisherDAO.readPublisherByPk(a.getPubId());
            a.setGenres(genres);
            a.setAuthors(authors);
            a.setPublisher(publisher);
        }
        return books;
    }

    @RequestMapping(value="/checkOutBook/{cardNo}/{bookId}/{branchId}",method = RequestMethod.GET, produces = "application/json")
    public void checkOutBook(@PathVariable int bookId,@PathVariable int cardNo, @PathVariable int branchId) throws SQLException, ClassNotFoundException {
        book_loanDAO.checkOutBook(bookId,branchId,cardNo);
    }
    @RequestMapping(value="/getBookLoanByBorrower/{cardNo}",method = RequestMethod.GET, produces = "application/json")
    public List<Book_Loan> getBookLoanByBorrower(@PathVariable int cardNo) throws SQLException, ClassNotFoundException {
        Book book;
        Borrower borrower;
        Library branch ;
        List<Book_Loan> book_loans= book_loanDAO.readBookLoanByCardNo(cardNo);
        for(Book_Loan book_loan:book_loans)
        {
            book=bookDAO.readBookByPk(book_loan.getBookId());
            borrower=borrowerDAO.readBorrowerByPk(book_loan.getCardNo());
            branch=libraryDAO.readLibraryByPk(book_loan.getBranchId());
            book_loan.setBook(book);
            book_loan.setBorrower(borrower);
            book_loan.setBranch(branch);
        }
        return book_loans;

    }
    @RequestMapping(value="/returnBook", method = RequestMethod.POST, consumes = "application/json")
    public void returnBook(@RequestBody Book_Loan book_loan) throws SQLException, ClassNotFoundException {
        book_loanDAO.returnBook(book_loan.getCardNo(),book_loan.getBookId(),book_loan.getBranchId(), book_loan.getDateOut());
    }
    @RequestMapping(value="/iniBookLoanList", method = RequestMethod.GET, produces = "application/json")
    public List<Book_Loan> iniBookLoanList() throws SQLException, ClassNotFoundException {
    List<Book_Loan> book_loans=new ArrayList<Book_Loan>();
    return book_loans;
    }

//
//    @GetMapping("/getBorrower")
//    public String getBorrowerByPk(@RequestParam("cardNo")int cardNo, @RequestParam("branchId")int branchId, Model model) throws SQLException, ClassNotFoundException {
//        Borrower borrower=borrowerDAO.readBorrowerByPk(cardNo);
//        Library library=new Library();
//        library.setLibraryId(branchId);
//        if(borrower!=null)
//        {
//        List<Genre> genres;
//        List<Author> authors;
//        Publisher publisher;
//        List<Book_Loan>  book_loans=book_loanDAO.readBookLoanByCardNo(cardNo);
//        for(Book_Loan a: book_loans)
//        {
//           Book book=bookDAO.readBookByPk(a.getBookId());
//           a.setBook(book);
//        }
//
//
//        List<Book> bookList=bookDAO.readBooksByBranch(branchId);
//        for(Book a:bookList)
//        {
//            genres=genreDAO.getGenresByBookId(a.getBookId());
//            authors=authorDAO.getAuthorsByBookId(a.getBookId());
//            publisher=publisherDAO.readPublisherByPk(a.getPubId());
//            a.setGenres(genres);
//            a.setAuthors(authors);
//            a.setPublisher(publisher);
//        }
//        model.addAttribute("book_loans",book_loans);
//        model.addAttribute("borrower",borrower);
//        model.addAttribute("books",bookList);
//        model.addAttribute("library",library);
//
//
//        return "borrowerMGMT";
//    }
//    else
//    return "borrower";
//    }
////need to change date format


//    //need to check date format
//    @GetMapping("/returnBook")
//    public String returnBook(@RequestParam("bookId")int bookId, @RequestParam("cardNo")int cardNo, @RequestParam("branchId")int branchId, @RequestParam("dateOut")Date dateOut,@RequestParam("realBranch")int realBranch, Model model) throws SQLException, ClassNotFoundException {
//        book_loanDAO.returnBook(cardNo,bookId,branchId,dateOut);
//        Borrower borrower=borrowerDAO.readBorrowerByPk(cardNo);
//            List<Genre> genres;
//            List<Author> authors;
//            Publisher publisher;
//            Library library=new Library();
//            library.setLibraryId(realBranch);
//
//            List<Book_Loan>  book_loans=book_loanDAO.readBookLoanByCardNo(cardNo);
//            for(Book_Loan a: book_loans)
//            {
//                Book book=bookDAO.readBookByPk(a.getBookId());
//                a.setBook(book);
//            }
//
//
//            List<Book> bookList=bookDAO.readBooksByBranch(realBranch);
//            for(Book a:bookList)
//            {
//                genres=genreDAO.getGenresByBookId(a.getBookId());
//                authors=authorDAO.getAuthorsByBookId(a.getBookId());
//                publisher=publisherDAO.readPublisherByPk(a.getPubId());
//                a.setGenres(genres);
//                a.setAuthors(authors);
//                a.setPublisher(publisher);
//            }
//            model.addAttribute("book_loans",book_loans);
//            model.addAttribute("borrower",borrower);
//            model.addAttribute("books",bookList);
//            model.addAttribute("library",library);
//
//
//        return "borrowerMGMT";
//    }

}
