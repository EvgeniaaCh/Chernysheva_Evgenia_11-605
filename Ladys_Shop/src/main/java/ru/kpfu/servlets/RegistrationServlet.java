package ru.kpfu.servlets;

import ru.kpfu.models.UserHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class RegistrationServlet extends HttpServlet {
    UserHandler uh = new UserHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (uh.checkSession(req)) {
            req.setAttribute("session", 1);
            resp.sendRedirect("/main");
        }else {
            getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try {
            if(!uh.checkError(req)) resp.sendRedirect("/main");
            else{getServletContext().getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);}
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
