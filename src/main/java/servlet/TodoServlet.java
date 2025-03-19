package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.Logger;
import storage.TaskStorage;

import java.io.IOException;
import java.util.Set;

@WebServlet("/to-do")
public class TodoServlet extends HttpServlet {

    private final TaskStorage taskStorage = new TaskStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            if (username == null) {
                Logger.log("Username not logged in");
                resp.sendRedirect("/login");
                return;
            }

            Set<String> tasks = taskStorage.getTasks(username);
            req.setAttribute("tasks", tasks);

            Logger.log("The transition to to-do.jsp has been completed");
            req.getRequestDispatcher("/page/to-do.jsp").forward(req, resp);

        } catch (Exception e) {
            Logger.log("Error in doGet: " + e.getMessage());
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");

            if (username == null) {
                Logger.log("Username not logged in");
                resp.sendRedirect("/login");
                return;
            }

            String task = req.getParameter("task");
            String deletedTask = req.getParameter("deletedTask");

            if (task != null && !task.isEmpty()) {
                taskStorage.addTask(username, task);
                Logger.log("The task " + task + " added to the task storage for user: " + username);
            }

            if (deletedTask != null && !deletedTask.isEmpty()) {
                taskStorage.removeTask(username, deletedTask);
                Logger.log("The task " + deletedTask + " removed from the task storage for user: " + username);
            }

            Set<String> tasks = taskStorage.getTasks(username);
            req.setAttribute("tasks", tasks);

            resp.sendRedirect("/to-do");

        } catch (Exception e) {
            Logger.log("Error in doPost: " + e.getMessage());
            throw new ServletException(e);
        }
    }
}