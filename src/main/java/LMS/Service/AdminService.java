package LMS.Service;


import LMS.DAO.*;
import LMS.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


public class AdminService {
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
	@Autowired
	PublisherDAO publisherDAO;
	@Autowired
	BookDAO bookDAO;

	@Transactional
	public void addAuthor(String authorName, int[] bookIds) throws SQLException, ClassNotFoundException {
		adao.addAuthor(authorName);
		int authorId=adao.searchAuthorIdAfterInsert(authorName);
		for (Integer id:bookIds)
		{
		adao.addAuthorBook(authorId,id);
		}

	}
	@Transactional
	public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {adao.updateAuthor(author);}
	@Transactional
	public void deleteAuthor(int authorId) throws SQLException, ClassNotFoundException {adao.deleteAuthor(authorId);}
	@Transactional
	public List<Author> readAuthors(Integer pageNo, String authorName) throws SQLException, ClassNotFoundException {
		List<Author> authors;
		if(authorName!=null)
			authors=adao.readAllAuthorsByName(authorName);
		else authors=adao.readAuthorsByPage(pageNo);
		for(Author a:authors)
		{
			List<Book> books=bdao.readByAuthor(a.getAuthorId());
			a.setBooks(books);
		}
		return authors;
	}
	@Transactional
	public List<Book> readBooks(Integer pageNo, String bookName,String publisherName,String genre,String authorName) throws SQLException, ClassNotFoundException {
		List<Book> books;
		if(pageNo!=null)
			books=bdao.readBooksByPage(pageNo);
		else
			books= bdao.readAllBooksByCondition(bookName,genre,authorName,publisherName);
		for(Book a:books)
		{
			Publisher publisher=publisherDao.readPublisherByPk(a.getPubId());
			List<Genre> genres=genreDAO.getGenresByBookId(a.getBookId());
			List<Author> authors=authorDAO.getAuthorsByBookId(a.getBookId());
			a.setPublisher(publisher);
			a.setAuthors(authors);
			a.setGenres(genres);
		}
		return books;
	}
	@Transactional
	public List<Book> readAllBooks() throws SQLException, ClassNotFoundException {return bdao.readAllBooks();}
	@Transactional
	public List<Genre> readAllGenres() throws SQLException, ClassNotFoundException {return genreDAO.readAllGenre();}
	@Transactional
	public List<Author> readAllAuthors() throws SQLException, ClassNotFoundException {return authorDAO.readAllAuthors();}
	@Transactional
	public Author readAuthorByPk(Integer authorId) throws SQLException, ClassNotFoundException {return adao.readAuthorByPk(authorId);}
	@Transactional
	public Book getBookByPk(Integer bookId) throws SQLException, ClassNotFoundException {return bdao.readBookByPk(bookId);}
	@Transactional
	public List<Publisher> getAllPublishers() throws SQLException, ClassNotFoundException {return publisherDao.readAllPublisher();}
	@Transactional
	public List<Book> getBooksByPublisher(int publisherId) throws SQLException, ClassNotFoundException {return bdao.readByPublisher(publisherId);}
	@Transactional
	public void editBook(int bookId, int publisherId,int[] genreIds, int[]authorIds){
		bdao.editBook(bookId,publisherId,genreIds,authorIds);
	}
	@Transactional
	public void deleteBook(int bookId) throws SQLException, ClassNotFoundException {bdao.deleteBook(bookId);}
	@Transactional
	public void addBook(String bookName,int publisherId,int[] authorIds,int[] genreIds) throws SQLException, ClassNotFoundException {
	    bdao.addBook(bookName,publisherId,authorIds,genreIds);
	    int bookId=bdao.searchBookByTitle(bookName);
	    for(Integer a:authorIds)
	        bdao.addBookAuthors(a,bookId);
	    for(Integer a:genreIds)
	        bdao.addBookGenres(a,bookId);
	}
	@Transactional
    public List<Book_Loan> readAllBookLoan(Integer pageNo,String borrowerName) throws SQLException, ClassNotFoundException {
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
    public Library getLibraryByPk(Integer libraryId) throws SQLException, ClassNotFoundException {return libraryDAO.readLibraryByPk(libraryId);}
	@Transactional
    public Borrower getBorrowerByPk(Integer cardNo) throws SQLException, ClassNotFoundException {return borrowerDAO.readBorrowerByPk(cardNo);}
    @Transactional
    public Book_Loan getBookLoanByPk(Integer bookId,Integer branchId,Integer cardNo) throws SQLException, ClassNotFoundException {return book_loanDAO.readBookLoanByPk(bookId,branchId,cardNo);}
    @Transactional
    public void updateBookLoan(Book_Loan book_loan) throws SQLException, ClassNotFoundException {book_loanDAO.updateBookLoan(book_loan);}
	@Transactional
	public Publisher getPublisherById(int publisherId) throws SQLException, ClassNotFoundException {return publisherDao.readPublisherByPk(publisherId);}
	@Transactional
	public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {publisherDAO.updatePublisher(publisher);}
	@Transactional
	public void deletePublisher(int publisherId) throws SQLException, ClassNotFoundException {publisherDAO.deletePublisher(publisherId);}
	@Transactional
	public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {publisherDao.addPublisher(publisher);}
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
	public void updateLibrary(Library library) throws SQLException, ClassNotFoundException {libraryDAO.updateLibaray(library);}
	@Transactional
	public void deleteLibrary(int libraryId) throws SQLException, ClassNotFoundException {libraryDAO.deleteLibrary(libraryId);}
	@Transactional
	public void addLibrary(Library library) throws SQLException, ClassNotFoundException {libraryDAO.addLibrary(library);}
	@Transactional
	public List<Borrower> getAllBorrowers() throws SQLException, ClassNotFoundException {return borrowerDAO.readAllBorrower();}
	@Transactional
	public List<Book> getBooksByBorrower(int cardNo) throws SQLException, ClassNotFoundException {return bookDAO.readByBorrower(cardNo);}
	@Transactional
    public void updateBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {borrowerDAO.updateBorrower(borrower);}

    public void deleteBorrower(int cardNo) throws SQLException, ClassNotFoundException {borrowerDAO.deleteBorrower(cardNo);}
    public void addBorrower(Borrower borrower) throws SQLException, ClassNotFoundException {borrowerDAO.addBorrower(borrower);}
    //	public void addBook(Book book) throws SQLException, ClassNotFoundException {
//		Integer bookId = bdao.addBookWithID(book);
//		if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
//			for (Author a : book.getAuthors()) {
//				bdao.addBookAuthors(a.getAuthorId(), bookId);
//			}
//		}
//	}
//
//

//

//
//	public void updateBook(Book book) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			BookDAO bookDAO=new BookDAO(conn);
//			bookDAO.updateBook(book);
//
//			if(book.getGenres()!=null && !book.getGenres().isEmpty())
//			{
//				for(Genre a:book.getGenres())
//					bookDAO.addBookGenres(a.getGenre_id(),book.getBookId());
//			}
//			if(book.getAuthors()!=null && !book.getAuthors().isEmpty()){
//				for(Author a: book.getAuthors()){
//					bookDAO.addBookAuthors(a.getAuthorId(),book.getBookId());
//				}
//			}
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//

//


//	public void updateCopy(Book_Copy book_copy) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_CopyDAO book_copyDAO=new Book_CopyDAO(conn);
//			book_copyDAO.updateCopy(book_copy);
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//	public void insertCopy(Book_Copy book_copy) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_CopyDAO book_copyDAO=new Book_CopyDAO(conn);
//			book_copyDAO.insertCopy(book_copy);
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//

//	public void returnBook(int cardNo,int bookId,int branchId) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_LoanDAO book_loanDAO=new Book_LoanDAO(conn);
//			book_loanDAO.returnBook(cardNo,bookId,branchId);
//			Book_CopyDAO book_copyDAO=new Book_CopyDAO(conn);
//			book_copyDAO.increase1(bookId,branchId);
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//
//
//
//	public void deleteAuthorByBookId(int bookId) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			AuthorDAO adao = new AuthorDAO(conn);
//			adao.deleteAuthorByBookId(bookId);
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//
//
//
//
//	public void deleteGenreByBookId(int bookId) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			GenreDAO genreDAO=new GenreDAO(conn);
//			genreDAO.deleteGenreByBookId(bookId);
//			conn.commit();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//			conn.rollback();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//



//	public List<Book> readBookSByBranch(int branchId) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//
//			BookDAO bookDAO=new BookDAO(conn);
//				return bookDAO.readBooksByBranch(branchId);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//
//
//	public List<Library> readLibraries(Integer pageNo, String libraryName) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			LibraryDAO libraryDAO=new LibraryDAO(conn);
//			if(libraryName!=null){
//				return libraryDAO.readLibrariesByName(libraryName);
//			}else{
//				return libraryDAO.readLibrariesByPage(pageNo);
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//

//
//	public void checkOutBook(int bookId,int branchId,int cardNo) throws SQLException {
//		Connection conn = null;
//		List<Library> libraries=new ArrayList<>();
//		try {
//			conn = util.getConnection();
//			Book_CopyDAO book_copyDAO=new Book_CopyDAO(conn);
//			book_copyDAO.checkOutBook(bookId,branchId,cardNo);
//			conn.commit();
//		}catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//	}
//	public List<Borrower> readBorrowers(Integer pageNo, String borrowerName) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			BorrowerDAO borrowerDAO=new BorrowerDAO(conn);
//			if(pageNo!=null)
//				return borrowerDAO.readBorrowersByPage(pageNo);
//			else
//				return borrowerDAO.readBorrowersByName(borrowerName);
//
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//	public List<Book_Loan> readBookLoans(Integer pageNo, String borrowerName) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_LoanDAO book_loanDAO=new Book_LoanDAO(conn);
//			if(pageNo!=null)
//				return book_loanDAO.readBookLoanByPage(pageNo);
//			else
//				return book_loanDAO.readBookLoanByName(borrowerName);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//
//	public List<Book_Loan> readBookLoanByCardNo(int cardNo) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_LoanDAO book_loanDAO=new Book_LoanDAO(conn);
//				return book_loanDAO.readBookLoanByCardNo(cardNo);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//
//
//	public List<Publisher> readPublishers(Integer pageNo, String publisherName)throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			PublisherDAO publisherDAO=new PublisherDAO(conn);
//			if(publisherName!=null){
//				return publisherDAO.readPublisherByName(publisherName);
//			}else{
//				return publisherDAO.readPublishersByPage(pageNo);
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//

//
//
//
//	public Publisher readPublisherByPk(Integer publisherId) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			PublisherDAO publisherDAO=new PublisherDAO(conn);
//			return publisherDAO.readPublisherByPk(publisherId);
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//


//	public Integer getAuthorsCount() throws SQLException{
//
//			return adao.getAuthorsCount();
//
//	}
//
//
//	public Integer getBooksCount() throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			BookDAO adao = new BookDAO(conn);
//			return adao.getBooksCount();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//	public Integer getPublishersCount() throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			PublisherDAO publisherDAO=new PublisherDAO(conn);
//			return publisherDAO.getPublishersCount();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//	public Integer getLibrariesCount() throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			LibraryDAO libraryDAO=new LibraryDAO(conn);
//			return libraryDAO.getLibrariesCount();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//	public Integer getBorrowersCount() throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			BorrowerDAO borrowerDAO=new BorrowerDAO(conn);
//			return borrowerDAO.getBorrowersCount();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//	public Integer getBookLoansCount() throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_LoanDAO book_loanDAO=new Book_LoanDAO(conn);
//			return book_loanDAO.getBookLoansCount();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//
//	public List<Book_Copy> getAllBookCopy() throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_CopyDAO book_copyDAO=new Book_CopyDAO(conn);
//			List<Book_Copy> book_copies=book_copyDAO.readAllBookCopy();
//			return book_copies;
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}
//	public Book_Copy readBookCopyByPk(int bookId,int branchId) throws SQLException{
//		Connection conn = null;
//		try {
//			conn = util.getConnection();
//			Book_CopyDAO book_copyDAO=new Book_CopyDAO(conn);
//			Book_Copy book_copy=book_copyDAO.readBookCopyByPk(bookId,branchId);
//			return book_copy;
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		} finally{
//			if(conn!=null)
//				conn.close();
//		}
//		return null;
//	}


}
