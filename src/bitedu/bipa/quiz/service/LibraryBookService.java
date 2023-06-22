package bitedu.bipa.quiz.service;

import bitedu.bipa.quiz.ListDTO;
import bitedu.bipa.quiz.dao.LibraryDAO;
import bitedu.bipa.quiz.dto.UserDTO;
import bitedu.bipa.quiz.vo.UseStatus;
import bitedu.bipa.quiz.vo.UserVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LibraryBookService {

    private LibraryDAO dao;

    public LibraryBookService() {
        dao = new LibraryDAO();
    }


//    public void rentBook(int book_seq, String user_id, String borrow_start, String borrow_end) {
//
//        boolean flag = dao.insertBookUseState(book_seq, user_id, borrow_start, borrow_end);
//
//        if (flag) {
//            System.out.println("대출이 완료되었습니다.");
//        } else {
//            System.out.println("이미 대출중인 책입니다..");
//        }
//
//    }
//
//    public void returnBook(int book_seq, String user_id, String return_date) {
//
//        boolean flag = dao.updateBookUseState(book_seq, user_id, return_date);
//
//        if (flag) {
//            System.out.println("반납이 완료되었습니다.");
//        } else {
//            System.out.println("반납에 실패하였습니다.");
//        }
//
//    }

    public JSONObject getUserInfo(String userId) {
        JSONObject result = new JSONObject();
        JSONObject obj = new JSONObject();
        // 전체 도서 목록 - 안은비 추가
        JSONArray totalBookList = getBorrowListByUserId(userId);
        obj.put("totalBookList", totalBookList);

        // 전체반납목록 - 박형석 추가
        JSONArray totalReturnList = getTotalReturnList(userId);
        obj.put("totalReturnList", totalReturnList);

        JSONArray OverdueBookList = getOverdueBookListByUserId(userId);
        obj.put("OverdueBookList", OverdueBookList);

        //반납 예정 목록 추가
        JSONArray returnList = getReturnList(userId);
        obj.put("soonReturnList", returnList);

        //사용자 정보 - 이지민
        JSONArray totalUserInfo = getTotalUserInfo(userId);
        obj.put("totalUserInfo", totalUserInfo);

        result.put("userData", obj);

        return result;
    }

    // 전체반납목록
    public JSONArray getTotalReturnList(String userId) {
        ArrayList<ListDTO> list = dao.totalReturnList(userId);

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

    // 반납예정목록
    public JSONArray getReturnList(String userId) {
        ArrayList<ListDTO> returnlist = dao.returnList(userId);

        JSONArray jsonArray = new JSONArray();

        for (ListDTO one : returnlist) {
            {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("book_seq", one.getBook_seq());
                jsonObject.put("book_title", one.getBook_title());
                jsonObject.put("book_author", one.getBook_author());
                jsonObject.put("borrow_start", one.getBorrow_start().toString());
                jsonObject.put("borrow_end", one.getBorrow_end().toString());

                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    // 전체 도서 목록 출력 - 안은비 추가
    public JSONArray getBorrowListByUserId(String userId) {
        ArrayList<ListDTO> lists = dao.selectBookListByUserId(userId);
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

    // 사용자 정보
    public JSONArray getTotalUserInfo(String userId) {
        UserDTO dto = dao.selectBookInfoByUser(userId);

        JSONObject innerJO = new JSONObject();
        JSONArray totalUser = new JSONArray();

        innerJO.put("status", dto.getStatus());
        innerJO.put("maxBook", dto.getMaxBook());
        innerJO.put("serviceStop", dto.getServiceStop().toString());

        totalUser.add(innerJO);

        return totalUser;
    }

    // 미반납 도서 목록 출력 - 김선규 추가
    public JSONArray getOverdueBookListByUserId(String userId) {
        ArrayList<ListDTO> lists = dao.getOverdueBookListByUserId(userId);
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

    // 도서대출
    public JSONObject borrowBook(String bookNum, String userId) throws SQLException {
        int bookNumParsed = Integer.parseInt(bookNum);
        String message = validateBorrow(bookNumParsed, userId);
        JSONObject jo = new JSONObject();

        if (message.equals("대출 가능")) {
            jo.put("result", dao.insertBookBorrow(bookNum, userId));
            return jo;
        } else {
            jo.put("result", message);
            return jo;
        }

    }

    // 도서 반납
    public JSONObject returnBook(String bookNum, String userId) throws SQLException {
        String message = validateReturn(bookNum, userId);
        JSONObject jo = new JSONObject();

        if(message.equals("반납 가능")){
            boolean isReturn = dao.updateBookReturn(bookNum, userId);
            addServiceStop(bookNum, userId);

            jo.put("result", isReturn);
            return jo;
        } else {
            jo.put("result", message);
            return jo;
        }

    }

    // 도서대출 예외처리
    public String validateBorrow(int bookNum, String userId) {
        // 사용자 정보 조회
        UserVO user = dao.selectUser(userId);
        UseStatus useStatus = dao.selectUseStatus(bookNum, userId);

        // 대출 가능한 책이 1개 이상일때
        if (user.getMaxBook() < 1) {
            return "대출 불가: 대출 가능한 책 초과";
        }
        // 대출 불가상태
        if (!(user.getUserState().equals("00"))) {
            return "대출 불가: 대출 불가상태";

        }
        // 같은책 일때
        if (useStatus.getBook_seq() == bookNum) {
            return "대출 불가: 빌렸던 책 입니다.";

        }
        // 대출 불가기간일때
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp currentTime = new Timestamp(currentTimeMillis);
        Timestamp serviceStop = user.getServiceStop();
        if (serviceStop != null) {
            int result = currentTime.compareTo(serviceStop);
            if (result < 0) {

                return "대출 불가: 대출 불가 기간입니다.";
            }
        }
        return "대출 가능";
    }


    // 도서반납 예외처리
    public String validateReturn(String bookNum, String userId) {
        int bookNumParsed = Integer.parseInt(bookNum);
        // 사용자 정보 조회
        UseStatus useStatus = dao.selectUseStatus(bookNumParsed, userId);

        // return date가 있을때
        if(useStatus.getReturn_date() != null) {
            return "이미 반납완료한 책입니다.";
        }

        return "반납 가능";
    }

    // 미반납 기간만큼 대출 중지
    public void addServiceStop(String bookNum, String userId) {
        int bookNumParsed = Integer.parseInt(bookNum);
        UseStatus useStatus = dao.selectUseStatus(bookNumParsed, userId);


        Date end = useStatus.getBorrow_end();
        Date returned = useStatus.getReturn_date();

        if (end.compareTo(returned) < 0) {
            long diffInMilliSec = Math.abs(returned.getTime() - end.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMilliSec, TimeUnit.MILLISECONDS);

            // returned에 end와 차이 일수를 더해줍니다
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(returned);
            calendar.add(Calendar.DATE, (int) diffInDays);
            returned = calendar.getTime();

            dao.updateReturnDate(returned, userId);
        }

    }


}
