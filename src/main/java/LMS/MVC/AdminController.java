package LMS.MVC;


import LMS.DAO.*;
import LMS.Entity.*;
import LMS.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by cj on 3/3/17.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    AuthorDAO adao;
    @Autowired
    BookDAO bdao;
    @Autowired
    GenreDAO genreDAO;
    @Autowired
    AuthorDAO authorDAO;
    @Autowired
    PublisherDAO publisherDAO;
    @Autowired
    Book_CopyDAO book_copyDAO;
    @Autowired
    Book_LoanDAO book_loanDAO;
    @Autowired
    BorrowerDAO borrowerDAO;
    @Autowired
    LibraryDAO libraryDAO;

    @Autowired
    BookDAO bookDAO;


    //author-----------------
    @RequestMapping(value="/getAllAuthors", method = RequestMethod.GET, produces = "application/json")
    public List<Author> getAllAuthors() throws SQLException, ClassNotFoundException {
        List<Author>authors= authorDAO.readAllAuthors();
        if (authors != null && !authors.isEmpty()) {
            for (Author a : authors) {
                a.setBooks(bdao.readByAuthor(a.getAuthorId()));
            }
        }
        return authors;
    }

    @RequestMapping(value="/getAllBooks", method = RequestMethod.GET, produces = "application/json")
    public List<Book> getAllBooks() throws SQLException, ClassNotFoundException {
        List<Book>books= bookDAO.readAllBooks();
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

    @RequestMapping(value = "/addAuthor", method=RequestMethod.POST, consumes = "application/json")
    public void addAuthor(@RequestBody Author author) throws SQLException, ClassNotFoundException {
        int authorId=adao.addAuthorkWithID(author.getAuthorName());
        for(Integer a:author.getBookIds())
            adao.addAuthorBook(authorId,a);


    }
    @RequestMapping(value = "/initAuthor", method = RequestMethod.GET, produces = "application/json")
    public Author initAuthor() throws SQLException, ClassNotFoundException {
        return new Author();
    }
    @RequestMapping(value = "/searchauthors/{authorName}", method = RequestMethod.GET, produces = "application/json")
    public List<Author> readAuthorsByName(@PathVariable String authorName) throws SQLException, ClassNotFoundException {

            List<Author> authors = adao.readAllAuthorsByName(authorName);
            if (authors != null && !authors.isEmpty()) {
                for (Author a : authors)
                    a.setBooks(bdao.readByAuthor(a.getAuthorId()));
        }
        return authors;
    }
    @RequestMapping(value = "/searchauthors", method = RequestMethod.GET, produces = "application/json")
    public List<Author> defaultSearch() throws SQLException, ClassNotFoundException{
        List<Author> authors= adao.readAllAuthors();
        for (Author a : authors)
            a.setBooks(bdao.readByAuthor(a.getAuthorId()));
    return authors;
    }

    @RequestMapping(value = "/deleteAuthor/{authorId}", method=RequestMethod.GET, produces = "application/json")
    public void deleteAuthor(@PathVariable int authorId) throws SQLException, ClassNotFoundException {
        adao.deleteAuthor(authorId);
    }

    @RequestMapping(value = "/editAuthor/{id}",method =RequestMethod.GET, produces = "application/json")
    public Author editAuthor(@PathVariable int id) throws SQLException, ClassNotFoundException {
        Author author= adao.readAuthorByPk(id);
        return author;

    }
    @RequestMapping(value = "/updateAuthor", method=RequestMethod.POST, consumes = "application/json")
    public void updateAuthor(@RequestBody Author author) throws SQLException, ClassNotFoundException {
        adao.updateAuthor(author);
    }
//-----------------------author done-----------

//    @RequestMapping("/borrower")
//    public String borrower() {
//        return "borrower";
//    }

//    @RequestMapping(value = "/editAuthor",method =RequestMethod.GET, consumes = "application/json")
//    public String editAuthor(@RequestParam("authorId") int authorId, Model theModel) throws SQLException, ClassNotFoundException {
//        Author author = adminService.readAuthorByPk(authorId);
//        theModel.addAttribute(author);
//        return "editauthor";
//    }



//    @RequestMapping("/editAuthor", method=RequestMethod.POST, )
//    public String editAuthor(@RequestParam("authorName") String authorName, @RequestParam("authorId") int authorId) throws SQLException, ClassNotFoundException {
//        Author author = new Author();
//        author.setAuthorId(authorId);
//        author.setAuthorName(authorName);
//        adminService.updateAuthor(author);
//        return "author";
//    }

//    @PostMapping("addAuthor")
//    public String editAuthor(@RequestParam("authorName") String authorName, @RequestParam("bookIds") int[] bookIds) throws SQLException, ClassNotFoundException {
//        adminService.addAuthor(authorName, bookIds);
//        return "author";
//    }




    //Book--------------------
    @RequestMapping(value = "/getBookByPk/{id}", method=RequestMethod.GET,produces = "application/json")
    public Book getBookByPk(@PathVariable int id) throws SQLException, ClassNotFoundException {
        Book book= bookDAO.readBookByPk(id);
        List<Genre> genres;
        List<Author> authors;
        Publisher publisher;
        genres=genreDAO.getGenresByBookId(book.getBookId());
        authors=authorDAO.getAuthorsByBookId(book.getBookId());
        publisher=publisherDAO.readPublisherByPk(book.getPubId());
        book.setGenres(genres);
        book.setAuthors(authors);
        book.setPublisher(publisher);
        return book;
    }


    @RequestMapping(value = "/initBook", method=RequestMethod.GET,produces = "application/json")
    public Book initBook() {return new Book();}

    @RequestMapping(value = "/searchBook/{title}", method = RequestMethod.GET, produces = "application/json")
    public List<Book> searchBook(@PathVariable String title) throws SQLException, ClassNotFoundException {
        List<Book> books = bookDAO.getBookByName(title);
        if (books != null && !books.isEmpty()) {
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
        }
        return books;
    }
    @RequestMapping(value = "/searchBook", method = RequestMethod.GET, produces = "application/json")
    public List<Book> searchAllBook() throws SQLException, ClassNotFoundException {
        List<Book> books = bookDAO.readAllBooks();
        if (books != null && !books.isEmpty()) {
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
        }
        return books;
    }

    @RequestMapping(value="/getAllPublisher",method = RequestMethod.GET, produces = "application/json")
    public List<Publisher> getAllPublisher() throws SQLException, ClassNotFoundException {
        List<Publisher> publishers= publisherDAO.readAllPublisher();
        if (publishers != null && !publishers.isEmpty()) {
            for(Publisher a:publishers)
                a.setBooks(bookDAO.readByPublisher(a.getPublisherId()));
        }
        return publishers;
    }
    @RequestMapping(value="/iniPublisher",method = RequestMethod.GET, produces = "application/json")
    public Publisher iniPublisher() throws SQLException, ClassNotFoundException {
        return new Publisher();
    }
    @RequestMapping(value="/updatePublisher",method = RequestMethod.POST, consumes = "application/json")
    public void updatePublisher(@RequestBody Publisher publisher) throws SQLException, ClassNotFoundException {
        publisherDAO.updatePublisher(publisher);
    }
    @RequestMapping(value="/deletePublisher/{id}",method = RequestMethod.GET, produces = "application/json")
    public void deletePublisher(@PathVariable int id) throws SQLException, ClassNotFoundException {
        publisherDAO.deletePublisher(id);
    }
    @RequestMapping(value="/addPublisher",method = RequestMethod.POST, consumes = "application/json")
    public void addPublisher(@RequestBody Publisher publisher) throws SQLException, ClassNotFoundException {
        publisherDAO.addPublisher(publisher);
    }


    @RequestMapping(value="/getPublisherByPk/{id}",method = RequestMethod.GET, produces = "application/json")
    public Publisher getPublisherByPk(@PathVariable int id) throws SQLException, ClassNotFoundException {
        Publisher publisher= publisherDAO.readPublisherByPk(id);
        if (publisher != null)
            publisher.setBooks(bookDAO.readByPublisher(publisher.getPublisherId()));
        return publisher;
    }



    @RequestMapping(value="/getAllGenre",method = RequestMethod.GET, produces = "application/json")
    public List<Genre> getAllGenre() throws SQLException, ClassNotFoundException {
        return genreDAO.readAllGenre();
    }
    @RequestMapping(value="/updateBook",method = RequestMethod.POST, consumes = "application/json")
    public void updateBook(@RequestBody Book book) throws SQLException, ClassNotFoundException {
       bookDAO.editBook(book.getTitle(),book.getBookId(),book.getPubId(),book.getGenreIds(),book.getAuthorIds());
    }
    @RequestMapping(value="/deleteBook/{id}",method = RequestMethod.GET, produces = "application/json")
    public void deleteBook(@PathVariable int id) throws SQLException, ClassNotFoundException {
        bookDAO.deleteBook(id);
    }

    @RequestMapping(value="/addBook",method = RequestMethod.POST, consumes = "application/json")
    public void addBook(@RequestBody Book book) throws SQLException, ClassNotFoundException {
        bookDAO.addBookWithID(book);
    }


    @RequestMapping("/book")
    public String book(Model model) throws SQLException, ClassNotFoundException {
 //       model.addAttribute("adminService", adminService);
        List<Genre> genreList=genreDAO.readAllGenre();
        List<Author> authorList=authorDAO.readAllAuthors();
        List<Publisher> publisherList=publisherDAO.readAllPublisher();
        model.addAttribute("genreList",genreList);
        model.addAttribute("authorList",authorList);

        return "book";
    }

//    @GetMapping("/editBook")
//    public String editBook(@RequestParam("bookId") int bookId, Model model) throws SQLException, ClassNotFoundException {
//        Book book = adminService.getBookByPk(bookId);
//        model.addAttribute("book", book);
//        return "editBook";
//    }

//    @PostMapping("/editBook")
//    public String editBook(@RequestParam("bookId") int bookId, @RequestParam("genreIds") int[] genreIds, @RequestParam("publisherId") int publisherId, @RequestParam("authorIds") int[] authorIds) {
//        adminService.editBook(bookId, publisherId, genreIds, authorIds);
//        return "book";
//    }







    //Change Due date



    @RequestMapping(value="/getAllBookLoans", method=RequestMethod.GET, produces = "application/json")
    public List<Book_Loan> getAllBookLoans() throws SQLException, ClassNotFoundException {
        Book book;
        Borrower borrower;
        Library branch ;
        List<Book_Loan>  book_loans= book_loanDAO.readAllLoanRecord();

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

    @RequestMapping(value = "/initBookLoan", method = RequestMethod.GET, produces = "application/json")
    public Book_Loan initBookLoan() throws SQLException, ClassNotFoundException {
        return new Book_Loan();
    }

    @RequestMapping(value = "/updateDueDate", method = RequestMethod.POST, consumes = "application/json")
    public void updateDueDate(@RequestBody Book_Loan book_loan_temp) throws SQLException, ClassNotFoundException {
        book_loanDAO.updateBookLoan(book_loan_temp);
    }


//    @RequestMapping(value="/editDue" method=RequestMethod.POST, consumes = "application/json")
//    public String editDue(@RequestBody Book_Loan book_loan) throws SQLException, ClassNotFoundException {
//        Book_Loan book_loan = adminService.getBookLoanByPk(bookId, branchId, cardNo);
//        Book book = bookDAO.readBookByPk(bookId);
//        Library branch = adminService.getLibraryByPk(branchId);
//        Borrower borrower = adminService.getBorrowerByPk(cardNo);
//        book_loan.setBranch(branch);
//        book_loan.setBook(book);
//        book_loan.setBorrower(borrower);
//        model.addAttribute("book_loan", book_loan);
//        return "editDueDate";
//    }

    @RequestMapping(value = "/editDue", method = RequestMethod.POST, consumes = "application/json",produces = "application/json")
    public Book_Loan editDue(@RequestBody Book_Loan book_loan) throws SQLException, ClassNotFoundException {
      Book_Loan book_loan1 =book_loanDAO.readBookLoanByPk(book_loan);
      return book_loan1;
    }


    //publisher ----------------
    @RequestMapping("/publisher")
    public String publisher(Model model) {
        model.addAttribute("adminService", adminService);
        return "publisher";
    }

    @GetMapping("/editPublisher/{id}")
    public String editPublisher(@PathVariable int id, Model model) throws SQLException, ClassNotFoundException {
        Publisher publisher = publisherDAO.readPublisherByPk(id);
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



    //Library --------------
    @RequestMapping(value = "/getLibraryById/{id}", method=RequestMethod.GET,produces = "application/json")
    public Library getLibraryById(@PathVariable int id) throws SQLException, ClassNotFoundException
    {return libraryDAO.readLibraryByPk(id);}




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
    //----------
    @RequestMapping(value = "/getBookCopyById/{branchId}/{bookId}", method=RequestMethod.GET,produces = "application/json")
    public Book_Copy getBookByPk(@PathVariable int branchId, @PathVariable int bookId) throws SQLException, ClassNotFoundException {
        return book_copyDAO.readBookCopyByPk(bookId,branchId);}


}