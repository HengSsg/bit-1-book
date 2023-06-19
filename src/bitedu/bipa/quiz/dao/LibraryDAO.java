package bitedu.bipa.quiz.dao;

import bitedu.bipa.quiz.ListDTO;
import bitedu.bipa.quiz.dto.UserDTO;
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
    
	// 반납예정목록
	public ArrayList<ListDTO> returnList() {
		ArrayList<ListDTO> list = new ArrayList<>();

		String sql = "SELECT bs.book_seq, bi.book_title, bi.book_author, bs.borrow_start, bs.borrow_end "
				+ "FROM book_info bi " + "JOIN book_copy bc ON bi.book_isbn = bc.book_isbn "
				+ "JOIN book_use_status bs ON bs.book_seq = bc.book_seq "
				+ "WHERE bs.user_id = 'User1' AND bs.borrow_end < CURDATE() AND bs.return_date IS NULL";

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

    // 전체 도서목록 - 안은비 추가
    public ArrayList<ListDTO> selectBookListByUserId(){
        ArrayList<ListDTO> bookList = new ArrayList<>();

        String sql = "SELECT BC.BOOK_SEQ, BI.BOOK_TITLE, BI.BOOK_AUTHOR, DATE(BS.BORROW_START), DATE(BS.BORROW_END)\n" +
                "FROM BOOK_USE_STATUS BS\n" +
                "JOIN BOOK_COPY BC\n" +
                "ON ( BS.BOOK_SEQ = BC.BOOK_SEQ) \n" +
                "JOIN BOOK_INFO BI\n" +
                "ON (BC.BOOK_ISBN = BI.BOOK_ISBN) \n" +
                "WHERE BS.USER_ID = ? ";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,"User1");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                ListDTO list = new ListDTO();
                list.setBook_seq(rs.getInt(1));
                list.setBook_title( rs.getString(2));
                list.setBook_author(rs.getString(3));
                list.setBorrow_start(rs.getDate(4));
                list.setBorrow_end( rs.getDate(5));

                bookList.add(list);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public UserDTO selectBookInfoByUser() {
        UserDTO dto = new UserDTO();
        String sql = "select user_status, max_book, service_stop from book_user where user_id='user1'";
        PreparedStatement ps = null;
        try {
            ps = this.con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                dto.setStatus(rs.getString(1));
                dto.setMaxBook(rs.getInt(2));
                dto.setServiceStop(rs.getDate(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dto;
    }
}
