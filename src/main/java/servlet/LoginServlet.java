package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Logger;
import storage.MockStorage;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("username");

            if (login == null || login.isEmpty()){
                req.getRequestDispatcher("/page/login.html").forward(req,resp);
                Logger.log("The transition to login.html has been completed");
            }else {
                req.getRequestDispatcher("/page/about-me.html").forward(req,resp);
                Logger.log("The transition to to-do.jsp has been completed");
            }

        } catch (ServletException e) {

            Logger.log(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isUserExist = MockStorage.doesUserExist(username, password);

        if (isUserExist) {
            Logger.log("User " + username + " is already logged in");

            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            req.getRequestDispatcher("/page/about-me.html").forward(req, resp);
        } else {
            Logger.log("User " + username + " does not exist");
            resp.sendRedirect("/page/login.html");
        }
    }
}
