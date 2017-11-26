package ru.kpfu.servlets;

import ru.kpfu.models.UserHandler;
import ru.kpfu.repositories.GoodDataBase;
import ru.kpfu.repositories.OrderDataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class CheckServlet extends HttpServlet {
    UserHandler uh = new UserHandler();
    Date date = new Date();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (uh.checkSession(req)) {
            req.setAttribute("session", 1);
            try {
                req.setAttribute("number_order", (OrderDataBase.getLastOrder()+1));
                req.setAttribute("total_sum", GoodDataBase.getTotalPrice(req.getSession().getAttribute("inputLogin").toString()));
                req.setAttribute("date_order",date.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/check.jsp").forward(req, resp);
        } else resp.sendRedirect("/input");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login= req.getSession().getAttribute("inputLogin").toString();
        try {
            OrderDataBase.addOrder(
                    login,
                    Float.valueOf(String.valueOf(GoodDataBase.getTotalPrice(login))),
                    date.toString()
            );
            GoodDataBase.removeAllGoods(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/basket");
    }
}