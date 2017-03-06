package LMS.MVC;


import LMS.DAO.LibraryDAO;
import LMS.Entity.*;
import LMS.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Created by cj on 3/3/17.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //author-----------------
    @RequestMapping("/author")
    public String author(Model model) throws SQLException, ClassNotFoundException {
        model.addAttribute("adminService", adminService);
        return "author";
    }

//    @RequestMapping("/borrower")
//    public String borrower() {
//        return "borrower";
//    }

    @GetMapping("/editAuthor")
    public String editAuthor(@RequestParam("authorId") int authorId, Model theModel) throws SQLException, ClassNotFoundException {
        Author author = adminService.readAuthorByPk(authorId);
        theModel.addAttribute(author);
        return "editauthor";
    }

    @PostMapping("/editAuthor")
    public String editAuthor(@RequestParam("authorName") String authorName, @RequestParam("authorId") int authorId) throws SQLException, ClassNotFoundException {
        Author author = new Author();
        author.setAuthorId(authorId);
        author.setAuthorName(authorName);
        adminService.updateAuthor(author);
        return "author";
    }

    @PostMapping("addAuthor")
    public String editAuthor(@RequestParam("authorName") String authorName, @RequestParam("bookIds") int[] bookIds) throws SQLException, ClassNotFoundException {
        adminService.addAuthor(authorName, bookIds);
        return "author";
    }

    @GetMapping("deleteAuthor")
    public String deleteAuthor(@RequestParam("authorId") int authorId) throws SQLException, ClassNotFoundException {
        adminService.deleteAuthor(authorId);
        return "author";
    }

    //Book--------------------
    @RequestMapping("/book")
    public String book(Model model) {
        model.addAttribute("adminService", adminService);
        return "book";
    }

    @GetMapping("/editBook")
    public String editBook(@RequestParam("bookId") int bookId, Model model) throws SQLException, ClassNotFoundException {
        Book book = adminService.getBookByPk(bookId);
        model.addAttribute("book", book);
        return "editBook";
    }

    @PostMapping("/editBook")
    public String editBook(@RequestParam("bookId") int bookId, @RequestParam("genreIds") int[] genreIds, @RequestParam("publisherId") int publisherId, @RequestParam("authorIds") int[] authorIds) {
        adminService.editBook(bookId, publisherId, genreIds, authorIds);
        return "book";
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam("bookId") int bookId) throws SQLException, ClassNotFoundException {
        adminService.deleteBook(bookId);
        return "book";
    }

    @PostMapping("/addBook")
    public String addBook(@RequestParam("bookName") String bookName, @RequestParam("authorIds") int[] authorIds, @RequestParam("genreIds") int[] genreIds, @RequestParam("publisherId") int publisherId) throws SQLException, ClassNotFoundException {
        adminService.addBook(bookName, publisherId, authorIds, genreIds);
        return "book";
    }
    //Change Due date

    @RequestMapping("/change_due")
    public String changeDue(Model model) {
        model.addAttribute("adminService", adminService);
        return "change_due";
    }

    @GetMapping("/editDue")
    public String editDue(@RequestParam("bookId") int bookId, @RequestParam("branchId") int branchId, @RequestParam("cardNo") int cardNo, Model model) throws SQLException, ClassNotFoundException {
        Book_Loan book_loan = adminService.getBookLoanByPk(bookId, branchId, cardNo);
        Book book = adminService.getBookByPk(bookId);
        Library branch = adminService.getLibraryByPk(branchId);
        Borrower borrower = adminService.getBorrowerByPk(cardNo);
        book_loan.setBranch(branch);
        book_loan.setBook(book);
        book_loan.setBorrower(borrower);
        model.addAttribute("book_loan", book_loan);
        return "editDueDate";
    }

    @PostMapping("/editDue")
    public String editDue(@RequestParam("bookId") int bookId, @RequestParam("branchId") int branchId, @RequestParam("cardNo") int cardNo, @RequestParam("dueDate") Date dueDate) throws SQLException, ClassNotFoundException {
        Book_Loan book_loan = adminService.getBookLoanByPk(bookId, branchId, cardNo);
        book_loan.setDueDate(dueDate);
        adminService.updateBookLoan(book_loan);
        return "change_due";
    }

    //publisher ----------------
    @RequestMapping("/publisher")
    public String publisher(Model model) {
        model.addAttribute("adminService", adminService);
        return "publisher";
    }

    @GetMapping("/editPublisher")
    public String editPublisher(@RequestParam("publisherId") int publisherId, Model model) throws SQLException, ClassNotFoundException {
        Publisher publisher = adminService.getPublisherById(publisherId);
        model.addAttribute("publisher", publisher);
        model.addAttribute("adminService", adminService);
        return "editPublisher";
    }

    @PostMapping("/editPublisher")
    public String editPublisher(@RequestParam("publisherId") int publisherId, @RequestParam("publisherName") String name, @RequestParam("publisherAddress") String address, @RequestParam("publisherPhone") String phone) throws SQLException, ClassNotFoundException {
        Publisher publisher = new Publisher();
        publisher.setPublisherAddress(address);
        publisher.setPublisherId(publisherId);
        publisher.setPublisherName(name);
        publisher.setPublisherPhone(phone);
        adminService.updatePublisher(publisher);
        return "redirect:/admin/publisher";
    }

    @GetMapping("/deletePublisher")
    public String deletePublisher(@RequestParam("publisherId") int publisherId, Model model) throws SQLException, ClassNotFoundException {
        adminService.deletePublisher(publisherId);
        return "redirect:/admin/publisher";
    }

    @PostMapping("/addPublisher")
    public String addAuthor(@RequestParam("publisherName") String name, @RequestParam("publisherAddress") String address, @RequestParam("publisherPhone") String phone) throws SQLException, ClassNotFoundException {
        Publisher publisher = new Publisher();
        publisher.setPublisherPhone(phone);
        publisher.setPublisherName(name);
        publisher.setPublisherAddress(address);
        adminService.addPublisher(publisher);
        return "redirect:/admin/publisher";
    }

    //Library --------------
    @RequestMapping("/library")
    public String library(Model model) {
        model.addAttribute("adminService", adminService);
        return "library";
    }

    @GetMapping("/editLibrary")
    public String editLibrary(@RequestParam("libraryId") int libraryId, Model model) throws SQLException, ClassNotFoundException {
        Library library = adminService.getLibraryByPk(libraryId);
        model.addAttribute("library", library);
        return "editLibrary";
    }

    @PostMapping("/editLibrary")
    public String editLibrary(@RequestParam("libraryId") int libraryId, @RequestParam("libraryName") String libraryName, @RequestParam("libraryAddress") String libraryAddress) throws SQLException, ClassNotFoundException {
        Library library = new Library();
        library.setLibraryId(libraryId);
        library.setLibraryAdderess(libraryAddress);
        library.setLibraryName(libraryName);
        adminService.updateLibrary(library);
        return "redirect:/admin/library";
    }

    @GetMapping("/deleteLibrary")
    public String deleteLibrary(@RequestParam("libraryId") int libraryId) throws SQLException, ClassNotFoundException {
        adminService.deleteLibrary(libraryId);
        return "redirect:/admin/library";
    }

    @PostMapping("/addLibrary")
    public String addLibrary(@RequestParam("libraryName") String libraryName, @RequestParam("libraryAddress") String libraryAddress) throws SQLException, ClassNotFoundException {
        Library library = new Library();
        library.setLibraryName(libraryName);
        library.setLibraryAdderess(libraryAddress);
        adminService.addLibrary(library);
        return "redirect:/admin/library";
    }
    //BORROWER -------
    @RequestMapping("/borrower")
    public String borrower(Model model){model.addAttribute("adminService", adminService); return "borrower";}
    @GetMapping("/editBorrower")
    public String editBorrower(@RequestParam("cardNo")int cardNo,Model model) throws SQLException, ClassNotFoundException {
        Borrower borrower=adminService.getBorrowerByPk(cardNo);
        model.addAttribute("borrower",borrower);
        return "editBorrower";
    }
    @PostMapping("/editBorrower")
    public String editBorrower(@RequestParam("cardNo")int cardNo,@RequestParam("borrowerName")String name,@RequestParam("borrowerAddress")String address,@RequestParam("borrowerPhone")String phone) throws SQLException, ClassNotFoundException {
        Borrower borrower=new Borrower();
        borrower.setAddress(address);
        borrower.setName(name);
        borrower.setCardNo(cardNo);
        borrower.setPhone(phone);
        adminService.updateBorrower(borrower);
        return "redirect:/admin/borrower";
    }
    @GetMapping("deleteBorrower")
    public String deleteBorrower(@RequestParam("cardNo")int cardNo) throws SQLException, ClassNotFoundException {adminService.deleteBorrower(cardNo);return "redirect:/admin/borrower";}
    @PostMapping("addBorrower")
    public String addBorrower(@RequestParam("borrowerName")String name,@RequestParam("borrowerAddress")String address,@RequestParam("borrowerPhone")String phone) throws SQLException, ClassNotFoundException {
    Borrower borrower=new Borrower();
    borrower.setPhone(phone);
    borrower.setName(name);
    borrower.setAddress(address);
    adminService.addBorrower(borrower);
    return "redirect:/admin/borrower";


    }

}