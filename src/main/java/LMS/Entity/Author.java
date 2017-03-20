package LMS.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class Author implements Serializable{
    private static final long serialVersionUID = -365418097942221990L;

    private Integer authorId;
    private String authorName;
    private List<Book> books;
    private int[] bookIds;

    public int[] getBookIds() {
        return bookIds;
    }

    public void setBookIds(int[] bookIds) {
        this.bookIds = bookIds;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

        Author author = (Author) o;

        if (!authorId.equals(author.authorId)) return false;
        return authorName.equals(author.authorName);
    }

    @Override
    public int hashCode() {
        int result = authorId.hashCode();
        result = 31 * result + authorName.hashCode();
        return result;
    }
}
