package bitedu.bipa.quiz;

import java.sql.Date;

public class ListDTO {
    private int book_seq;
    private String book_title;
    private String book_author;
    private Date borrow_start;
    private Date borrow_end;

    public ListDTO() {

    }
    public ListDTO(int book_seq, String book_title, String book_author, Date borrow_start, Date borrow_end) {
        this.book_seq = book_seq;
        this.book_title = book_title;
        this.book_author = book_author;
        this.borrow_start = borrow_start;
        this.borrow_end = borrow_end;
    }

    public void hahaah() {

    }
    public int getBook_seq() {
        return book_seq;
    }

    public void setBook_seq(int book_seq) {
        this.book_seq = book_seq;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public Date getBorrow_start() {
        return borrow_start;
    }

    public void setBorrow_start(Date borrow_start) {
        this.borrow_start = borrow_start;
    }

    public Date getBorrow_end() {
        return borrow_end;
    }

    public void setBorrow_end(Date borrow_end) {
        this.borrow_end = borrow_end;
    }
}
