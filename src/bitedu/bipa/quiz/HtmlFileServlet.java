package bitedu.bipa.quiz;

import bitedu.bipa.quiz.service.LibraryBookService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/bookService")
public class HtmlFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String result = getUserInfo("User1");

        response.getWriter().println(result);
    }

    private String getUserInfo(String userId) {
        String data = null;

        LibraryBookService libraryBookService = new LibraryBookService();

        JSONObject result = new JSONObject();
        JSONObject obj = new JSONObject();
        // 전체 도서 목록 - 안은비 추가
        JSONArray totalBookList = libraryBookService.getBorrowListByUserId();
        obj.put("totalBookList", totalBookList);

        // 전체반납목록 - 박형석 추가
        JSONArray totalReturnList = libraryBookService.getTotalReturnList();
        obj.put("totalReturnList", totalReturnList);

        JSONArray OverdueBookList = libraryBookService.getOverdueBookListByUserId();
        obj.put("overdueBookList", OverdueBookList);

        //반납 예정 목록 추가
        JSONArray returnList = libraryBookService.getReturnList();
        obj.put("soonReturnList", returnList);

        //사용자 정보 - 이지민
        JSONArray totalUserInfo = libraryBookService.getTotalUserInfo();
        obj.put("totalUserInfo", totalUserInfo);


        data = result.toJSONString();
        System.out.println(data);

        return data;
    }
}
