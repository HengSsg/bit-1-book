package bitedu.bipa.quiz;

import bitedu.bipa.quiz.util.DatabaseConnection;
import bitedu.bipa.quiz.vo.BookCopyVO;
import bitedu.bipa.quiz.vo.BookInfoVO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddData {

    Connection conn;

    public AddData() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public static void main(String[] args) throws IOException {
        AddData addData = new AddData();
        // book_info
        ArrayList<BookInfoVO> list = addData.addDatabase();
        addData.saveDatabase(list);

        // book_copy
        ArrayList<BookCopyVO> list2 = addData.addDatabaseCopy();
        addData.saveDatabaseCopy(list2);
    }


    public ArrayList<BookInfoVO> addDatabase() throws IOException {
        ArrayList<BookInfoVO> list = new ArrayList<>();

        File file = new File("/Users/hengssg/Java-Spring/bit/bit-1-book/data/book_info.csv");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        while ((line = br.readLine()) != null) {
            String[] temp = line.split(",");
            String book_isbn = temp[0].trim().replace("\"", "");
            ;
            String title = temp[1].trim().replace("\"", "");
            ;
            String book_author = temp[2].trim().replace("\"", "");
            ;
            String book_published_date = temp[3].trim().replace("\"", "");
            ;

            list.add(
                    new BookInfoVO(book_isbn, title, book_author, book_published_date)
            );
        }
        return list;
    }

    public ArrayList<BookCopyVO> addDatabaseCopy() throws IOException {
        ArrayList<BookCopyVO> list = new ArrayList<>();

        File file = new File("/Users/hengssg/Java-Spring/bit/bit-1-book/data/book_copy.csv");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = null;

        while ((line = br.readLine()) != null) {
            String[] temp = line.split(",");
            int book_seq = Integer.parseInt(temp[0].trim());
            String book_position = temp[1].trim().replace("\"", "");
            String book_status = temp[2].trim().replace("\"", "");
            String book_isbn = temp[3].trim().replace("\"", "");
            list.add(
                    new BookCopyVO(book_seq, book_position, book_status, book_isbn)
            );
        }
        return list;
    }

    public void saveDatabase(ArrayList<BookInfoVO> list) {

        try {
            String sql = "insert into book_info values(?, ?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (BookInfoVO bookInfoVO : list) {
                LocalDate localDate = LocalDate.parse(bookInfoVO.getBook_published_date());
                java.sql.Date sqlDate = Date.valueOf(localDate);

                System.out.println(bookInfoVO.getBook_published_date());
                pstmt.setString(1, bookInfoVO.getBook_isbn());
                pstmt.setString(2, bookInfoVO.getTitle());
                pstmt.setString(3, bookInfoVO.getBook_author());
                pstmt.setDate(4, sqlDate);
                pstmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveDatabaseCopy(ArrayList<BookCopyVO> list) {

        try {
            String sql = "insert into book_copy values(?, ?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (BookCopyVO bookCopyVO : list) {
                System.out.println(bookCopyVO.getBook_isbn());
                pstmt.setInt(1, bookCopyVO.getBook_seq());
                pstmt.setString(2, bookCopyVO.getBook_position());
                pstmt.setString(3, bookCopyVO.getBook_status());
                pstmt.setString(4, bookCopyVO.getBook_isbn());
                pstmt.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
