package LMS.MVC;

import LMS.DAO.*;
import LMS.Entity.Book_Copy;
import LMS.Entity.Library;
import LMS.Service.BorrowerService;
import LMS.Service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;

/**
 * Created by cj on 3/3/17.
 */
@Controller
@RequestMapping("/librarian")
public class LibrarianController {
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
    @Autowired
    Book_CopyDAO book_copyDAO;
    @Autowired
    LibrarianService librarianService;

    @GetMapping("/editLibrary")
    public String editLibrary(@RequestParam("libraryId")int libraryId, Model model) throws SQLException, ClassNotFoundException {
        Library library=libraryDAO.readLibraryByPk(libraryId);
        model.addAttribute("library",library);
        return "editLibrary";
    }
    @PostMapping("/editLibrary")
    public String editLibrary(@RequestParam("libraryId")int libraryId,@RequestParam("libraryName")String libraryName,@RequestParam("libraryAddress")String libraryAddress) throws SQLException, ClassNotFoundException {
        Library library=new Library();
        library.setLibraryId(libraryId);
        library.setLibraryAdderess(libraryAddress);
        library.setLibraryName(libraryName);
        libraryDAO.updateLibaray(library);
        return "redirect:/librarian";
    }
    @PostMapping("/checkCopy")
    public String checkCopy(@RequestParam("bookId")int bookId, @RequestParam("branchId")int branchId, Model model) throws SQLException, ClassNotFoundException {
        Book_Copy book_copy=book_copyDAO.readBookCopyByPk(bookId,branchId);
        if(book_copy==null)
        {
            Book_Copy book_copy1=new Book_Copy();
            book_copy1.setBookId(bookId);
            book_copy1.setBranchId(branchId);
            book_copy1.setNoOfCopies(0);
            model.addAttribute("book_copy",book_copy1);
            return "checkBookCopy";
        }
        else
        model.addAttribute("book_copy",book_copy);
        return "checkBookCopy";
    }

    @PostMapping("/updateCopy")
    public String updateCopy(@RequestParam("bookId")int bookId, @RequestParam("branchId")int branchId,@RequestParam("noOfCopy")int noOfCopy) throws SQLException, ClassNotFoundException {
        Book_Copy book_copy=book_copyDAO.readBookCopyByPk(bookId,branchId);
        if(book_copy==null)
        {
            Book_Copy book_copy1=new Book_Copy();
            book_copy1.setBookId(bookId);
            book_copy1.setBranchId(branchId);
            book_copy1.setNoOfCopies(noOfCopy);
            book_copyDAO.insertCopy(book_copy1);
            return "redirect:/librarian";
        }
        else
            book_copy.setNoOfCopies(noOfCopy);
            book_copyDAO.updateCopy(book_copy);
        return "redirect:/librarian";
    }

}
