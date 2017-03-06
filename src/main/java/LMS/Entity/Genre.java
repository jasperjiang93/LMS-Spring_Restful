package LMS.Entity;



import java.io.Serializable;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class Genre implements Serializable {
    private static final long serialVersionUID = -1463656733862378384L;
    private int genre_id;
    private String genre_name;
    private List<Book> books;

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (genre_id != genre.genre_id) return false;
        return genre_name != null ? genre_name.equals(genre.genre_name) : genre.genre_name == null;
    }

    @Override
    public int hashCode() {
        int result = genre_id;
        result = 31 * result + (genre_name != null ? genre_name.hashCode() : 0);
        return result;
    }
}
