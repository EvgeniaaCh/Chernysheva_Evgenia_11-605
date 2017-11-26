package ru.kpfu.servlets;

import ru.kpfu.entites.CatalogGood;
import ru.kpfu.models.UserHandler;
import ru.kpfu.repositories.CatalogDataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class GoodInfoServlet extends HttpServlet {
    UserHandler handler = new UserHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CatalogGood catalogGood;
        if (handler.checkSession(req)) {
            req.setAttribute("session", 1);
        }
        try {
            int catalogId = Integer.valueOf(req.getParameter("catalogId"));
            catalogGood = CatalogDataBase.getInfoFromCatalog(catalogId);
            req.setAttribute("catalogId", catalogId);
            req.setAttribute("good_name", catalogGood.getName());
            req.setAttribute("good_price", catalogGood.getPrice());
            req.setAttribute("good_description", catalogGood.getDescription());
            req.setAttribute("img_good", catalogGood.getImg());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/goodInfo.jsp").forward(req, resp);
    }
}