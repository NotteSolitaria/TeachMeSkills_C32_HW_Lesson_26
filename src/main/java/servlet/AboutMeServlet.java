package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Logger;

import java.io.IOException;

@WebServlet("/about-me")
public class AboutMeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            if (username == null) {
                resp.sendRedirect("/login");
            } else {
                Logger.log("The transition to about-me.html has been completed");

                req.getRequestDispatcher("/page/about-me.html").forward(req, resp);
            }
        } catch (ServletException e) {
            Logger.log(e.getMessage());
            throw e;
        }
    }

}