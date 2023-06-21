package bitedu.bipa.quiz.vo;

public class BookCopyVO {
    private int book_seq;
    private String book_position;
    private String book_status;
    private String book_isbn;


    public BookCopyVO(int book_seq, String book_position, String book_status, String book_isbn) {
        this.book_seq = book_seq;
        this.book_position = book_position;
        this.book_status = book_status;
        this.book_isbn = book_isbn;
    }

    public int getBook_seq() {
        return book_seq;
    }

    public void setBook_seq(int book_seq) {
        this.book_seq = book_seq;
    }

    public String getBook_position() {
        return book_position;
    }

    public void setBook_position(String book_position) {
        this.book_position = book_position;
    }

    public String getBook_status() {
        return book_status;
    }

    public void setBook_status(String book_status) {
        this.book_status = book_status;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }
}
