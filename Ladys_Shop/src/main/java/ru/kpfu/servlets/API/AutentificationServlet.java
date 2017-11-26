package ru.kpfu.servlets.API;

import ru.kpfu.entites.Artist;
import ru.kpfu.models.API.Parser;
import ru.kpfu.models.API.WeatherAPI;
import ru.kpfu.models.UserHandler;
import org.json.JSONException;
import ru.kpfu.repositories.UserDataBase;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class AutentificationServlet extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserHandler handler = new UserHandler();
        UserDataBase db = new UserDataBase();
        WeatherAPI api = new WeatherAPI();
        Parser parser = new Parser();
        String weatherData = "";
        String listenerData = "";

        if (handler.checkSession(req)) {

            try {
                weatherData = api.getWeatherData(db.getCity(req));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                req.setAttribute("temperature", parser.getWeather(weatherData));
                req.setAttribute("city", db.getCity(req));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                listenerData = api.getSizeLoveSinger(db.getFavoriteSinger(req));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                req.setAttribute("sizeListening", parser.getSizeListener(listenerData));
                req.setAttribute("favoriteSinger", db.getFavoriteSinger(req));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ArrayList<Artist> artists = new ArrayList<Artist>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("D://artists.txt")));
            int c;
            while ((c=reader.read()) != -1) {
                String nameArtist="";
                nameArtist += (char) c;
                while (((char)(c=reader.read()))!= ',' && c != -1) {
                    nameArtist += (char) c;
                }
                try {
                    artists.add(new Artist(
                            parser.getNameArtist(api.getSizeLoveSinger(nameArtist)),
                            parser.getSizeListener(api.getSizeLoveSinger(nameArtist)),
                            parser.getImgArtist(api.getSizeLoveSinger(nameArtist))
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                nameArtist="";
                reader.skip(1);
            }
            reader.close();
            req.setAttribute("artists", artists);


            getServletContext().getRequestDispatcher("/WEB-INF/views/autentification.jsp").forward(req, resp);

        } else resp.sendRedirect("/input");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("Catalog") != null) {
            resp.sendRedirect("/catalog");
        }
        if (req.getParameter("basket") != null) {
            resp.sendRedirect("/basket");
        }
    }
}
