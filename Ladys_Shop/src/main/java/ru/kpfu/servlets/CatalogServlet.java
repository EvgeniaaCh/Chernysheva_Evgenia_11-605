package ru.kpfu.servlets;

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
public class CatalogServlet extends HttpServlet {
    UserHandler uh = new UserHandler();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (uh.checkSession(req)) {
        if (uh.checkSession(req)) {
            req.setAttribute("session", 1);
        }
        try {
            String type = req.getParameter("type");
            req.setAttribute("catalog_goods", CatalogDataBase.getAllGoodsFromCatalog(type));
            type = type.substring(0, 1).toUpperCase() + type.substring(1);
            req.setAttribute("type", type);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
//        } else resp.sendRedirect("/input");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name_good");

    }
}