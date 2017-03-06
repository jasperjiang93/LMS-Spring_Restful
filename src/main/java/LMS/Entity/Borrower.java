package LMS.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class Borrower implements Serializable {
    private static final long serialVersionUID = -4165707314476806592L;

    private int cardNo;
    private String name;
    private String address;
    private String phone;
    private List<Book> books; //book that borrower holds


    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

        Borrower borrower = (Borrower) o;

        if (cardNo != borrower.cardNo) return false;
        return name != null ? name.equals(borrower.name) : borrower.name == null;
    }

    @Override
    public int hashCode() {
        int result = cardNo;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
