package bitedu.bipa.quiz.dao;

import bitedu.bipa.quiz.ListDTO;
import bitedu.bipa.quiz.util.ConnectionManager;
import bitedu.bipa.quiz.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryDAO {

    private Connection con;


    public LibraryDAO() {
        this.con = ConnectionManager.getConnection();
    }


    // 도서 대출
    public boolean insertBookUseState(int book_seq, String user_id, String borrow_start, String borrow_end) {
        boolean flag = false;
        String sql = "insert into book_use_status(book_seq, user_id, borrow_start, borrow_end) values(?, ?, ?, ?);";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, book_seq);
            pstmt.setString(2, user_id);
            pstmt.setString(3, borrow_start);
            pstmt.setString(4, borrow_end);
            int affectedCount = pstmt.executeUpdate();
            if (affectedCount > 0) {
                flag = true;
            }
            ConnectionManager.closeConnection(null, pstmt, con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    // 도서 반납
    public boolean updateBookUseState(int book_seq, String user_id, String return_date) {
        boolean flag = false;
        String sql = "update book_use_status set return_date=? where user_id=? and book_seq=?;";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(2, user_id);
            pstmt.setString(1, return_date);
            pstmt.setInt(3, book_seq);
            int affectedCount = pstmt.executeUpdate();
            if (affectedCount > 0) {
                flag = true;
            }
            ConnectionManager.closeConnection(null, pstmt, con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    // 메소드 이름: selectBookInfoByUser 파라메터와 리턴타입은 분석에 의해 자유롭게
    public void selectBookInfoByUser() {


    }


    public UserVO selectUser(String user_id) {
        UserVO userVO = null;
        String sql = "select * from book_user where user_id=?;";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            userVO = new UserVO();
            while (rs.next()) {

                userVO.setUserSeq(rs.getInt("user_seq"));
                userVO.setUserId(rs.getString("user_id"));
                userVO.setUserPass(rs.getString("user_pass"));
                userVO.setUserPhoneNumber(rs.getString("user_phone_number"));
                userVO.setUserState(rs.getString("user_status"));
                userVO.setUserGrade(rs.getString("user_grade"));
                userVO.setMaxBook(rs.getInt("max_book"));
                userVO.setServiceStop(rs.getTimestamp("service_stop"));

            }


        } catch (SQLException e) {

        }
        return userVO;

    }

    // 전체반납목록
    public ArrayList<ListDTO> totalReturnList() {
        ArrayList<ListDTO> list = new ArrayList<>();
        String sql = "SELECT bs.book_seq, bi.book_title, bi.book_author, bs.borrow_start, bs.borrow_end\n" +
                "FROM book_info bi\n" +
                "JOIN book_copy bc ON bi.book_isbn = bc.book_isbn\n" +
                "JOIN book_use_status bs ON bs.book_seq = bc.book_seq\n" +
                "WHERE bs.user_id = ? and bs.return_date is not null;";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "User1");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ListDTO listDTO = new ListDTO();
                listDTO.setBook_seq(rs.getInt("book_seq"));
                listDTO.setBook_title(rs.getString("book_title"));
                listDTO.setBook_author(rs.getString("book_author"));
                listDTO.setBorrow_start(rs.getDate("borrow_start"));
                listDTO.setBorrow_end(rs.getDate("borrow_end"));
                list.add(listDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
