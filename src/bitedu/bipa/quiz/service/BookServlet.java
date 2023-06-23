package bitedu.bipa.quiz.service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/info")
public class BookServlet extends HttpServlet{

    LibraryBookService lbs = null;

    @Override
    public void init() {
        lbs = new LibraryBookService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(this.lbs.getUserInfo(userId));
        out.close();
    }
}


