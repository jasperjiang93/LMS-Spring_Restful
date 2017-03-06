package LMS.DAO;

import LMS.Entity.Author;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>> {

    public void addAuthor(String authorName) throws SQLException, ClassNotFoundException {
        template.update("insert into tbl_author (authorName) values (?)", new Object[] {authorName});
    }
    public int searchAuthorIdAfterInsert(String authorName) {
        List<Author> authors =  template.query("select * from tbl_author where authorName= ?", new Object[]{authorName},this);
            return authors.get(0).getAuthorId();
    }
    public void addAuthorBook(int authorId, int bookId){template.update("insert into tbl_book_authors values(?,?)", new Object[] {bookId,authorId});
    }
//
//    public Integer addAuthorWithId(Author author) throws SQLException, ClassNotFoundException {
//       return  template.update("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
//    }


    public void updateAuthor(Author author) throws SQLException, ClassNotFoundException {
        template.update("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
    }

    public void deleteAuthor(int authorId) throws SQLException, ClassNotFoundException {
        template.update("delete from tbl_author where authorId = ?", new Object[] {authorId});
    }
    public void deleteAuthorByBookId(int bookId) throws SQLException, ClassNotFoundException {
        template.update("delete from tbl_book_authors where bookId = ?", new Object[] {bookId});
    }
    public List<Author> readAuthorsByPage(Integer pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        return  template.query("select * from tbl_author", this);
    }
    public List<Author> readAllAuthors() throws ClassNotFoundException, SQLException{
        return  template.query("select * from tbl_author", this);
    }
    public List<Author> readAllAuthorsByName(String authorName) throws ClassNotFoundException, SQLException{
        return  template.query("select * from tbl_author where authorName like ?", new Object[]{"%"+authorName+"%"},this);
    }
    public List<Author> getAuthorsByBookId(int bookId){
        return template.query("select * from tbl_author where authorId in(select authorId from tbl_book_authors where bookId=?)",
                new Object[]{bookId},this);
    }
    public Author readAuthorByPk(Integer authorId) throws ClassNotFoundException, SQLException{
        List<Author> authors =  template.query("select * from tbl_author where authorId = ?", new Object[]{authorId},this);
        if(authors!=null && !authors.isEmpty()){
            return authors.get(0);
        }
        return null;
    }





    @Override
    public List<Author> extractData(ResultSet rs) throws SQLException{
        List<Author> authors = new ArrayList<Author>();
        while(rs.next()){
            Author a = new Author();
            a.setAuthorId(rs.getInt("authorId"));
            a.setAuthorName(rs.getString("authorName"));
            authors.add(a);
        }
        return authors;
    }



}

