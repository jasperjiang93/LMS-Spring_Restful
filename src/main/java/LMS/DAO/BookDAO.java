package LMS.DAO;

import LMS.Entity.*;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class BookDAO extends BaseDAO implements ResultSetExtractor<List<Book>> {



//    public void addBookDetails(String bookName,int publisherId,int[] authorIds,int[] genreIds) throws SQLException, ClassNotFoundException{

    public int searchBookByTitle(String title){
        List<Book> books =  template.query("select * from tbl_book where title= ?", new Object[]{title},this);
        return books.get(0).getBookId();
    }
    public void addBookWithID(Book book) throws ClassNotFoundException, SQLException{
        final String title=book.getTitle();
        final int pubId=book.getPubId();
        final String INSERT_SQL = "insert into tbl_book (title, pubId) values (? , ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "bookId" });
                ps.setString(1, title);
                ps.setInt(2,pubId);
                return ps;
            }
        }, keyHolder);
        int bookId=keyHolder.getKey().intValue();

        for(Integer a:book.getGenreIds())
            template.update("insert into tbl_book_genres values(?,?)",new Object[]{a,bookId});
        for(Integer a:book.getAuthorIds())
            template.update("insert into tbl_book_authors values(?,?)",new Object[]{bookId,a});
    }
    public void addBookAuthors(Integer authorId, Integer bookId) throws ClassNotFoundException, SQLException{
        template.update("insert into tbl_book_authors values (? , ?)", new Object[]{bookId, authorId});
    }

    public void addBookGenres(Integer genreID, Integer bookId) throws ClassNotFoundException, SQLException{
        template.update("insert into tbl_book_genres values (? , ?)", new Object[]{genreID, bookId});
    }


    public void updateBook(Book book) throws SQLException, ClassNotFoundException{
    template.update("update tbl_book set title=?,pubId=? where bookId=?",new Object[]{book.getTitle(),book.getPublisher().getPublisherId(),book.getBookId()});
    }
    public void updateBookPubId(int pid,int bookId) throws SQLException, ClassNotFoundException{
        template.update("update tbl_book set pubId=? where bookId=?",new Object[]{pid,bookId});
    }

    public void deleteBook(int bookId) throws ClassNotFoundException, SQLException{
        template.update("delete from tbl_book where bookId = ? ", new Object[]{bookId});
    }

    public List<Book> readAllBooksByCondition(String bookName,String genre,String authorName, String publisherName) throws ClassNotFoundException, SQLException{

        if(bookName==null)
          bookName="%";
      else
          bookName="%"+bookName+"%";
      if(genre==null)
          genre="%";
      if(authorName==null)
          authorName="%";
      if(publisherName==null)
          publisherName="%";
        return template.query("\n" +
                "select distinct book.bookid,book.title, book.pubId from tbl_book book\n" +
                "left join tbl_book_authors ba\n" +
                "on  book.bookId=ba.bookId\n" +
                "left join tbl_author author\n" +
                "on author.authorId=ba.authorID\n" +
                "left join tbl_book_genres bg\n" +
                "on bg.bookId=book.bookId\n" +
                "left join tbl_genre genre\n" +
                "on genre.genre_id=bg.genre_id\n" +
                "left join tbl_publisher publisher\n" +
                "on publisher.publisherId=book.pubId\n" +
                "where book.title like  ?\n" +
                "and genre.genre_name like ?\n" +
                "and author.authorName like ?\n" +
                "and publisher.publisherName like ?", new Object[]{bookName,genre,authorName,publisherName},this);
    }

    public List<Book> getBookByName(String title){
        return template.query("select * from tbl_book where title like?",new Object[]{"%"+title+"%"},this);
    }



    public List<Book> readBooksByPage(Integer pageNo) throws ClassNotFoundException, SQLException{
            setPageNo(pageNo);
            return template.query("select * from tbl_book", this);
    }
    public List<Book> readAllBooks() throws ClassNotFoundException, SQLException{
        List<Book> books= template.query("select * from tbl_book", this);
//        List<Genre> genres;
//        List<Author> authors;
//        Publisher publisher;
//        GenreDAO genreDAO=new GenreDAO();
//        PublisherDAO publisherDAO=new PublisherDAO();
//        AuthorDAO authorDAO=new AuthorDAO();
//        for(Book a:books)
//        {
//            genres=genreDAO.getGenresByBookId(a.getBookId());
//            authors=authorDAO.getAuthorsByBookId(a.getBookId());
//            publisher=publisherDAO.readPublisherByPk(a.getPubId());
//            a.setGenres(genres);
//            a.setAuthors(authors);
//            a.setPublisher(publisher);
//        }
        return books;
    }
    public Book readBookByPk(Integer bookId) throws ClassNotFoundException, SQLException{
        List<Book> books = template.query("select * from tbl_book where bookId = ?", new Object[]{bookId},this);
        if(books!=null && !books.isEmpty()){
            return books.get(0);
        }
        return null;
    }
    public void editBook(String title,int bookId, int publisherId,int[] genreIds, int[]authorIds){
        template.update("update tbl_book set title=?, pubId=? where bookId=?",new Object[]{title,publisherId,bookId});
        template.update("delete from tbl_book_genres where bookId=?",new Object[]{bookId});
        for(Integer a:genreIds)
            template.update("insert into tbl_book_genres values(?,?)",new Object[]{a,bookId});
        template.update("delete from tbl_book_authors where bookId=?",new Object[]{bookId});
        for(Integer a:authorIds)
            template.update("insert into tbl_book_authors values(?,?)",new Object[]{bookId,a});
    }


    public List<Book> readBooksByBranch(int branchId) throws ClassNotFoundException, SQLException{
        List<Book> books= template.query("select * from tbl_book where bookId IN(select bookId from tbl_book_copies where branchId=?)",new Object[]{branchId},this);
//        List<Genre> genres;
//        List<Author> authors;
//        Publisher publisher;
//        GenreDAO genreDAO=new GenreDAO();
//        PublisherDAO publisherDAO=new PublisherDAO();
//        AuthorDAO authorDAO=new AuthorDAO();
//        for(Book a:books)
//        {
//            genres=genreDAO.getGenresByBookId(a.getBookId());
//            authors=authorDAO.getAuthorsByBookId(a.getBookId());
//            publisher=publisherDAO.readPublisherByPk(a.getPubId());
//            a.setGenres(genres);
//            a.setAuthors(authors);
//            a.setPublisher(publisher);
//        }
        return books;
    }

    public List<Book> readByBorrower(int cardNo) throws ClassNotFoundException, SQLException{
        return  template.query("select * from tbl_book where bookId IN(select bookId from tbl_book_loans where cardNo=?)",new Object[]{cardNo},this);
    }


    public List<Book> readByPublisher(int publisherId) throws ClassNotFoundException, SQLException{
        return  template.query("select * from tbl_book where pubId=?",new Object[]{publisherId},this);
    }

    public List<Book> readByAuthor(int authorId)  throws ClassNotFoundException, SQLException{
        return template.query("select * from tbl_book where bookId IN(select bookId from tbl_book_authors where authorId=?)"
                ,new Object[]{authorId},this);
    }



//
//    public Integer getBooksCount() throws ClassNotFoundException, SQLException{
//        return getCount("tbl_book");
//    }
//

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<Book>();
        while(rs.next()){
            Book b = new Book();
            b.setTitle(rs.getString("title"));
            b.setBookId(rs.getInt("bookId"));
            b.setPubId(rs.getInt("pubId"));
//            b.setGenres((List<Genre>) gdao.readAllFirstLevel("select * from tbl_genre where genre_Id IN(select genre_Id from tbl_book_genres where bookId = ?)", new Object[]{b.getBookId()}));
//
//            if(rs.getInt("pubId") > 0){
//                b.setPublisher(pdao.readPublisherByPk(rs.getInt("pubId")));
//            }
//            b.setAuthors((List<Author>) adao.readAllFirstLevel("select * from tbl_author where authorId IN(select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
            //poopulate genres
            books.add(b);
        }
        return books;
    }


}
