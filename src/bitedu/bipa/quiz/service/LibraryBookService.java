package bitedu.bipa.quiz.service;

import bitedu.bipa.quiz.ListDTO;
import bitedu.bipa.quiz.dao.LibraryDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryBookService {

    private LibraryDAO dao;

    public LibraryBookService() {
        dao = new LibraryDAO();
    }


    public void rentBook(int book_seq, String user_id, String borrow_start, String borrow_end) {

        boolean flag = dao.insertBookUseState(book_seq, user_id, borrow_start, borrow_end);

        if (flag) {
            System.out.println("대출이 완료되었습니다.");
        } else {
            System.out.println("이미 대출중인 책입니다..");
        }

    }

    public void returnBook(int book_seq, String user_id, String return_date) {

        boolean flag = dao.updateBookUseState(book_seq, user_id, return_date);

        if (flag) {
            System.out.println("반납이 완료되었습니다.");
        } else {
            System.out.println("반납에 실패하였습니다.");
        }

    }

    public void getUserInfo() {
        JSONObject obj = new JSONObject();
        // 전체 도서 목록 - 안은비 추가
        JSONArray totalBookList = getBorrowListByUserId();
        obj.put("totalBookList", totalBookList);

        // 전체반납목록
        JSONArray totalReturnList = getTotalReturnList();
        obj.put("totalReturnList", totalReturnList);

        try (FileWriter file = new FileWriter("/Users/hengssg/Downloads/박형석/exam/data2.json")) {
            file.write(obj.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //    전체반납목록
    public JSONArray getTotalReturnList() {
        ArrayList<ListDTO> list = dao.totalReturnList();

        JSONArray jsonArray = new JSONArray();
        for (ListDTO one : list) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("book_seq", one.getBook_seq());
            jsonObject.put("book_title", one.getBook_title());
            jsonObject.put("book_author", one.getBook_author());
            jsonObject.put("borrow_start", one.getBorrow_start().toString());
            jsonObject.put("borrow_end", one.getBorrow_end().toString());

            jsonArray.add(jsonObject);

        }

        return jsonArray;
    }

    // 전체 도서 목록 출력 - 안은비 추가
    public JSONArray getBorrowListByUserId() {
        ArrayList<ListDTO> lists = dao.selectBookListByUserId();
        JSONArray totals = new JSONArray();
        for (ListDTO list : lists) {
            JSONObject total = new JSONObject();
            total.put("book_seq", list.getBook_seq());
            total.put("book_title", list.getBook_title());
            total.put("book_author", list.getBook_author());
            total.put("borrow_start", list.getBorrow_start().toString());
            total.put("borrow_end", list.getBorrow_end().toString());

            totals.add(total);
        }
        return totals;
    }
}
