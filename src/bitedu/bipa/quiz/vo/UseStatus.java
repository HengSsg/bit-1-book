package bitedu.bipa.quiz.vo;

import java.util.Date;

public class UseStatus {
    private int book_seq;
    private String user_id;
    private Date borrow_start;
    private Date borrow_end;
    private Date return_date;

    public UseStatus() {
    }

    public UseStatus(int book_seq, String user_id, Date borrow_start, Date borrow_end, Date return_date) {
        this.book_seq = book_seq;
        this.user_id = user_id;
        this.borrow_start = borrow_start;
        this.borrow_end = borrow_end;
        this.return_date = return_date;
    }

    public int getBook_seq() {
        return book_seq;
    }

    public void setBook_seq(int book_seq) {
        this.book_seq = book_seq;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }
}
