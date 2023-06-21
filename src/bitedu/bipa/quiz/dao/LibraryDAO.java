package bitedu.bipa.quiz.dao;

import bitedu.bipa.quiz.ListDTO;
import bitedu.bipa.quiz.dto.UserDTO;
import bitedu.bipa.quiz.util.ConnectionManager;
import bitedu.bipa.quiz.vo.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public ArrayList<ListDTO> totalReturnList(String userId) {
        ArrayList<ListDTO> list = new ArrayList<>();
        String sql = "SELECT bs.book_seq, bi.book_title, bi.book_author, bs.borrow_start, bs.borrow_end\n" +
                "FROM book_info bi\n" +
                "JOIN book_copy bc ON bi.book_isbn = bc.book_isbn\n" +
                "JOIN book_use_status bs ON bs.book_seq = bc.book_seq\n" +
                "WHERE bs.user_id = ? and bs.return_date is not null;";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);
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
	public ArrayList<ListDTO> returnList(String userId) {
		ArrayList<ListDTO> list = new ArrayList<>();

		String sql = "SELECT bs.book_seq, bi.book_title, bi.book_author, bs.borrow_start, bs.borrow_end "
				+ "FROM book_info bi " + "JOIN book_copy bc ON bi.book_isbn = bc.book_isbn "
				+ "JOIN book_use_status bs ON bs.book_seq = bc.book_seq "
				+ "WHERE bs.user_id = ? AND bs.borrow_end < CURDATE() AND bs.return_date IS NULL";

		try {

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
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
    public ArrayList<ListDTO> selectBookListByUserId(String userId){
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
            pstmt.setString(1,userId);
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
    
    // 미반납 도석 목록 - 김선규 추가
    public ArrayList<ListDTO> getOverdueBookListByUserId(String userId){
        ArrayList<ListDTO> bookList = new ArrayList<>();

        String sql = "SELECT book_use_status.book_seq, book_info.book_title, book_info.book_author, book_use_status.borrow_start, book_use_status.borrow_end\n"
    			+ "FROM book_use_status\n"
    			+ "INNER JOIN book_copy ON book_use_status.book_seq = book_copy.book_seq\n"
    			+ "INNER JOIN book_info ON book_copy.book_isbn = book_info.book_isbn\n"
    			+ "where book_use_status.borrow_end < curdate() and book_use_status.return_date IS NULL and book_use_status.user_id = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,userId);
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

    public UserDTO selectBookInfoByUser(String userId) {
        UserDTO dto = new UserDTO();
        String sql = "select user_status, max_book, service_stop from book_user where user_id='"+userId+"';";
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


    public boolean insertBookBorrow(String bookNum, String userId) throws SQLException {
        PreparedStatement ps = null;
        StringBuilder updateBookStateSQLBuilder = new StringBuilder();
        updateBookStateSQLBuilder.append("update book_copy set book_position = 'BB-");
        updateBookStateSQLBuilder.append(userId);
        updateBookStateSQLBuilder.append("' where book_seq = ?;");

        String insertBorrowSQL = "insert into book_use_status(book_seq,user_id,borrow_start,borrow_end) values(?, ?, ?, ?);";
        String updateBookStateSQL = updateBookStateSQLBuilder.toString();
        String updateUserStateSQL = "update book_user set max_book = max_book - 1 where user_id = ?;";

        try {
            this.con.setAutoCommit(false);

            //책 대여 기록 입력
            LocalDate curDate = LocalDate.now();
            LocalDate returnDate = getReturnDate(curDate);

            ps = this.con.prepareStatement(insertBorrowSQL);
            ps.setString(1, bookNum);
            ps.setString(2, userId);
            ps.setString(3, curDate.toString());
            ps.setString(4, returnDate.toString());
            int insertBorrowResult = ps.executeUpdate();
            if(insertBorrowResult < 1) {
                return false;
            }

            //책 상태 변경
            ps = this.con.prepareStatement(updateBookStateSQL);
            ps.setString(1, bookNum);
            int updateBookStateResult = ps.executeUpdate();
            if(updateBookStateResult < 1) {
                return false;
            }

            //사용자 상태 변경
            ps = this.con.prepareStatement(updateUserStateSQL);
            ps.setString(1, userId);
            int updateUserStateResult = ps.executeUpdate();
            if(updateUserStateResult < 1) {
                return false;
            }
        } catch (SQLException e) {
            this.con.rollback();
        } finally {
            this.con.setAutoCommit(true);
        }
        return true;
    }

    public boolean updateBookReturn(String bookNum, String userId) throws SQLException {
        PreparedStatement ps = null;

        String updateBorrowSQL = "update book_use_status set return_date = ? where user_id = ? and book_seq = ?;";
        String updateBookStateSQL = "update book_copy set book_position = 'BS-0001' where book_seq = ?;";
        String updateUserStateSQL = "update book_user set max_book = max_book + 1 where user_id = ?;";

        try {
            this.con.setAutoCommit(false);

            //책 대여 기록 입력
            LocalDate curDate = LocalDate.now();

            ps = this.con.prepareStatement(updateBorrowSQL);
            ps.setString(1, curDate.toString());
            ps.setString(2, userId);
            ps.setString(3, bookNum);
            int updateBorrowResult = ps.executeUpdate();
            if(updateBorrowResult < 1) {
                return false;
            }

            //책 상태 변경
            ps = this.con.prepareStatement(updateBookStateSQL);
            ps.setString(1, bookNum);
            int updateBookStateResult = ps.executeUpdate();
            if(updateBookStateResult < 1) {
                return false;
            }

            //사용자 상태 변경
            ps = this.con.prepareStatement(updateUserStateSQL);
            ps.setString(1, userId);
            int updateUserStateResult = ps.executeUpdate();
            if(updateUserStateResult < 1) {
                return false;
            }
        } catch (SQLException e) {
                this.con.rollback();
        }   finally {
                this.con.setAutoCommit(true);
        }
        return true;
    }

    private LocalDate getReturnDate(LocalDate date) {
        LocalDate returnDate = date.plus(14, ChronoUnit.DAYS);
        return returnDate;
    }
}
