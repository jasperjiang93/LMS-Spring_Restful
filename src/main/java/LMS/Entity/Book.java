package LMS.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class Book implements Serializable {
    private static final long serialVersionUID = -7392430255985002517L;
    private int bookId;
    private String title;
    private int pubId;
    private Publisher publisher;
    private List<Author> authors;
    private List<Genre> genres;
    private int[] authorIds;
    private int[] genreIds;

    public int[] getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(int[] authorIds) {
        this.authorIds = authorIds;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }



    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (bookId != book.bookId) return false;
        if (pubId != book.pubId) return false;
        return title != null ? title.equals(book.title) : book.title == null;
    }

    @Override
    public int hashCode() {
        int result = bookId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + pubId;
        return result;
    }
}
