package LMS.MVC;

import LMS.DAO.*;
import LMS.Entity.*;
import LMS.Service.BorrowerService;
import LMS.Service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.SQLException;

import java.util.List;

/**
 * Created by cj on 3/3/17.
 */
@Controller
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

    @GetMapping("/getBorrower")
    public String getBorrowerByPk(@RequestParam("cardNo")int cardNo, @RequestParam("branchId")int branchId, Model model) throws SQLException, ClassNotFoundException {
        Borrower borrower=borrowerDAO.readBorrowerByPk(cardNo);
        if(borrower!=null)
        {
        List<Genre> genres;
        List<Author> authors;
        Publisher publisher;
        List<Book_Loan>  book_loans=book_loanDAO.readBookLoanByCardNo(cardNo);
        for(Book_Loan a: book_loans)
        {
           Book book=bookDAO.readBookByPk(a.getBookId());
           a.setBook(book);
        }


        List<Book> bookList=bookDAO.readBooksByBranch(branchId);
        for(Book a:bookList)
        {
            genres=genreDAO.getGenresByBookId(a.getBookId());
            authors=authorDAO.getAuthorsByBookId(a.getBookId());
            publisher=publisherDAO.readPublisherByPk(a.getPubId());
            a.setGenres(genres);
            a.setAuthors(authors);
            a.setPublisher(publisher);
        }
        model.addAttribute("book_loans",book_loans);
        model.addAttribute("borrower",borrower);
        model.addAttribute("books",bookList);

        return "borrowerMGMT";
    }
    else
    return "borrower";
    }
//need to change date format
    @GetMapping("/checkOutBook")
    public String checkOutBook(@RequestParam("bookId")int bookId,@RequestParam("cardNo")int cardNo, @RequestParam("branchId")int branchId,Model model) throws SQLException, ClassNotFoundException {
        book_loanDAO.checkOutBook(bookId,branchId,cardNo);
        Borrower borrower=borrowerDAO.readBorrowerByPk(cardNo);
        List<Genre> genres;
        List<Author> authors;
        Publisher publisher;
        List<Book_Loan>  book_loans=book_loanDAO.readBookLoanByCardNo(cardNo);
        for(Book_Loan a: book_loans)
        {
            Book book=bookDAO.readBookByPk(a.getBookId());
            a.setBook(book);
        }


        List<Book> bookList=bookDAO.readBooksByBranch(branchId);
        for(Book a:bookList)
        {
            genres=genreDAO.getGenresByBookId(a.getBookId());
            authors=authorDAO.getAuthorsByBookId(a.getBookId());
            publisher=publisherDAO.readPublisherByPk(a.getPubId());
            a.setGenres(genres);
            a.setAuthors(authors);
            a.setPublisher(publisher);
        }
        model.addAttribute("book_loans",book_loans);
        model.addAttribute("borrower",borrower);
        model.addAttribute("books",bookList);

        return "borrowerMGMT";
    }
    //need to check date format
    @GetMapping("/returnBook")
    public String returnBook(@RequestParam("bookId")int bookId, @RequestParam("cardNo")int cardNo, @RequestParam("branchId")int branchId, @RequestParam("dateOut")Date dateOut, Model model) throws SQLException, ClassNotFoundException {
        book_loanDAO.returnBook(cardNo,bookId,branchId,dateOut);
        Borrower borrower=borrowerDAO.readBorrowerByPk(cardNo);
            List<Genre> genres;
            List<Author> authors;
            Publisher publisher;
            List<Book_Loan>  book_loans=book_loanDAO.readBookLoanByCardNo(cardNo);
            for(Book_Loan a: book_loans)
            {
                Book book=bookDAO.readBookByPk(a.getBookId());
                a.setBook(book);
            }


            List<Book> bookList=bookDAO.readBooksByBranch(branchId);
            for(Book a:bookList)
            {
                genres=genreDAO.getGenresByBookId(a.getBookId());
                authors=authorDAO.getAuthorsByBookId(a.getBookId());
                publisher=publisherDAO.readPublisherByPk(a.getPubId());
                a.setGenres(genres);
                a.setAuthors(authors);
                a.setPublisher(publisher);
            }
            model.addAttribute("book_loans",book_loans);
            model.addAttribute("borrower",borrower);
            model.addAttribute("books",bookList);

            return "borrowerMGMT";
    }

}
