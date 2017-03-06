package LMS.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class Library implements Serializable{
    private static final long serialVersionUID = 1423041858581975314L;

    private int libraryId;
    private String libraryName;
    private String libraryAdderess;
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getLibraryAdderess() {
        return libraryAdderess;
    }

    public void setLibraryAdderess(String libraryAdderess) {
        this.libraryAdderess = libraryAdderess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Library library = (Library) o;

        if (libraryId != library.libraryId) return false;
        return libraryName != null ? libraryName.equals(library.libraryName) : library.libraryName == null;
    }

    @Override
    public int hashCode() {
        int result = libraryId;
        result = 31 * result + (libraryName != null ? libraryName.hashCode() : 0);
        return result;
    }
}
