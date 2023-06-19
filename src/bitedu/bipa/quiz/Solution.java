package bitedu.bipa.quiz;

import bitedu.bipa.quiz.service.LibraryBookService;

import java.io.IOException;

public class Solution {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Solution solution = new Solution();
		solution.getUserInfo("User1");
	}
	
	public void getUserInfo(String userId) throws IOException {
		// 도서이용현황에 대한 정보를 가져와서
		LibraryBookService service = new LibraryBookService();
		service.getUserInfo();
	}


}
