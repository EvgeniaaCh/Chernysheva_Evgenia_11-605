package ru.kpfu.servlets;

import ru.kpfu.models.UserHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class InputServlet extends HttpServlet {
    UserHandler handler = new UserHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handler.checkSession(req)) {
            req.setAttribute("session", 1);
            resp.sendRedirect("/main");
        } else getServletContext().getRequestDispatcher("/WEB-INF/views/input.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handler.checkDataForAutentification(req)) {
            req.setAttribute("session", 1);
            getServletContext().getRequestDispatcher("/WEB-INF/views/mainPage.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/views/input.jsp").forward(req, resp);
        }
    }
}
