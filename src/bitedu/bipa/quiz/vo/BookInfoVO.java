package bitedu.bipa.quiz.vo;

import java.util.Date;

public class BookInfoVO {

    private String book_isbn;
    private String title;
    private String book_author;
    private String book_published_date;

    public BookInfoVO(String book_isbn, String title, String book_author, String book_published_date) {
        this.book_isbn = book_isbn;
        this.title = title;
        this.book_author = book_author;
        this.book_published_date = book_published_date;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_published_date() {
        return book_published_date;
    }

    public void setBook_published_date(Date book_published_date) {
        this.book_published_date = String.valueOf(book_published_date);
    }
}
