package ru.kpfu.servlets.CRUD;

import ru.kpfu.entites.Good;
import ru.kpfu.models.UserHandler;
import ru.kpfu.repositories.GoodDataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class GoodAddServlet extends HttpServlet {
    UserHandler uh = new UserHandler();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (uh.checkSession(req)) {
            try {
                int quantity = Integer.valueOf(req.getParameter("quantity"));
                String login = req.getSession().getAttribute("inputLogin").toString();
                int id = Integer.valueOf(req.getParameter("id"));
                Good good = new Good(id,
                        quantity, login);
                GoodDataBase.addGood(good);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resp.sendRedirect("/basket");
        } else resp.sendRedirect("/input");
    }
}
