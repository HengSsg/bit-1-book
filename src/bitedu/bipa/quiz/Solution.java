package bitedu.bipa.quiz;

import bitedu.bipa.quiz.service.LibraryBookService;

import java.io.IOException;
import java.sql.SQLException;

public class Solution {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Solution solution = new Solution();
//		solution.getUserInfo("User1");

		try {
//			solution.borrowFunc("7", "User1");
			solution.returnFunc("4", "User1");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void getUserInfo(String userId) throws IOException {
		// 도서이용현황에 대한 정보를 가져와서
		LibraryBookService service = new LibraryBookService();
		service.getUserInfo(userId);
	}

	public void borrowFunc(String bookNum, String userId) throws SQLException {
		// 도서이용현황에 대한 정보를 가져와서
		LibraryBookService service = new LibraryBookService();
		service.borrowBook(bookNum, userId);
	}

	public void returnFunc(String bookNum, String userId) throws SQLException {
		// 도서이용현황에 대한 정보를 가져와서
		LibraryBookService service = new LibraryBookService();
		service.returnBook(bookNum, userId);
	}

}
