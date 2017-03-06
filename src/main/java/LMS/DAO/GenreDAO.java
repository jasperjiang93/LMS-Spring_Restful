package LMS.DAO;


import LMS.Entity.Genre;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>> {

    public void addGenre(Genre genre) throws SQLException, ClassNotFoundException {}
    public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {}
    public void deleteGenre(Genre genre) throws SQLException, ClassNotFoundException {}
    public void deleteGenreByBookId(int bookId)throws SQLException, ClassNotFoundException {
        template.update("delete from tbl_book_genres where bookId=?",new Object[]{bookId});
    }
    public List<Genre> readAllGenre()throws SQLException, ClassNotFoundException {
        return template.query("select * from tbl_genre",this);
    }
    public List<Genre> getGenresByBookId(int bookId){
        return template.query("select * from tbl_genre where genre_Id in(select genre_Id from tbl_book_genres where bookId=?)",
                new Object[]{bookId},this);
    }


    @Override
    public List<Genre> extractData(ResultSet rs) throws SQLException{
        List<Genre> genres = new ArrayList<Genre>();
        BookDAO bdao = new BookDAO();
        while(rs.next()){
            Genre a = new Genre();
            a.setGenre_id(rs.getInt("genre_id"));
            a.setGenre_name(rs.getString("genre_name"));
            genres.add(a);
        }
        return genres;
    }



}
