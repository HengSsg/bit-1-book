package bitedu.bipa.quiz.service;

import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/borrow")
public class BookBorrowServlet extends HttpServlet{
    LibraryBookService lbs = null;

    @Override
    public void init() {
        lbs = new LibraryBookService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("시작");
        String bookNum = req.getParameter("bookNum");
        String userId = req.getParameter("userId");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        JSONObject result = null;
        try {
            result = this.lbs.borrowBook(bookNum, userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        out.print(result);
        out.close();
    }
}
