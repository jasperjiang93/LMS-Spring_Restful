package LMS.MVC;

import LMS.DAO.*;
import LMS.Entity.Book_Copy;
import LMS.Entity.Library;
import LMS.Service.BorrowerService;
import LMS.Service.LibrarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by cj on 3/3/17.
 */
@RestController
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

    @RequestMapping(value="/libraryList", method = RequestMethod.GET, produces = "application/json")
    public List<Library> libraryList() throws SQLException, ClassNotFoundException {
        List<Library> libraries=libraryDAO.readAllLibaray();
        for (Library a : libraries)
            a.setBooks(bookDAO.readBooksByBranch(a.getLibraryId()));
            return libraries;
    }

    @RequestMapping(value="/updateLibrary", method = RequestMethod.POST, consumes = "application/json")
    public void updateLibrary(@RequestBody Library library) throws SQLException, ClassNotFoundException {
        libraryDAO.updateLibaray(library);
    }
    @RequestMapping(value = "/searchLibraries/{libraryName}", method = RequestMethod.GET, produces = "application/json")
    public List<Library> readLibrariesByName(@PathVariable String libraryName) throws SQLException, ClassNotFoundException {

        List<Library> libraries=libraryDAO.readLibrariesByName(libraryName);
        if (libraries != null && !libraries.isEmpty()) {
            for (Library a : libraries)
                a.setBooks(bookDAO.readBooksByBranch(a.getLibraryId()));
        }
        return libraries;
    }
    @RequestMapping(value = "/searchLibraries", method = RequestMethod.GET, produces = "application/json")
    public List<Library> defaultSearch() throws SQLException, ClassNotFoundException {
        List<Library> libraries=libraryDAO.readAllLibaray();
            for (Library a : libraries)
                a.setBooks(bookDAO.readBooksByBranch(a.getLibraryId()));
        return libraries;
    }
    @RequestMapping(value = "/iniBookCopy", method = RequestMethod.GET, produces = "application/json")
    public Book_Copy iniBookCopy(){return new Book_Copy();}

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

    @RequestMapping(value = "/updateBookCopy", method=RequestMethod.POST,produces = "application/json")
    public void updateCopy(@RequestBody Book_Copy book_copy) throws SQLException, ClassNotFoundException {
        Book_Copy book_copy1=book_copyDAO.readBookCopyByPk(book_copy.getBookId(),book_copy.getBranchId());
        if(book_copy1==null)
            book_copyDAO.insertCopy(book_copy);
        else
            book_copyDAO.updateCopy(book_copy);
    }

}
