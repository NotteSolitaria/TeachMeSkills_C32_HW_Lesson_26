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

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String login = (String) session.getAttribute("username");

            if (login == null || login.isEmpty()) {
                Logger.log("The transition to registration.html has been completed");
                req.getRequestDispatcher("/page/registration.html").forward(req, resp);
            } else {
                Logger.log("The transition to to-do.jsp has been completed");
                req.getRequestDispatcher("/page/to-do.jsp").forward(req, resp);
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

        boolean userExists = MockStorage.doesUserExist(username, password);

        if (userExists) {
            Logger.log("The username and password are in base");
            req.getRequestDispatcher("/page/registration.html").forward(req, resp);
        } else {
            MockStorage.addUser(username, password);
            req.getSession().setAttribute("username", username);
            Logger.log("The username" + username + " has been registered");
            resp.sendRedirect("/to-do");
        }
    }
}
