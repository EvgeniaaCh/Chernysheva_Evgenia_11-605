package ru.kpfu.repositories;

import ru.kpfu.models.DBWrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class OrderDataBase {
    private static Connection conn;
    private static ResultSet rs;

    //ToDo сделать нормальное задавание номера заказа
    public static int getLastOrder() throws SQLException {
        int numberOrder = 0;
        conn = DBWrapper.getConection();
        rs = conn.createStatement().executeQuery("SELECT * FROM orders " +
                "WHERE order_id = (SELECT MAX(order_id) from orders)");
        while (rs.next()) {
            numberOrder = rs.getInt("order_id");
        }
        return numberOrder;
    }

    public static void addOrder(String login, float total_price, String date) throws SQLException {
        conn = DBWrapper.getConection();
        rs = conn.createStatement().executeQuery(
                "select c.catalog_good_id, g.quantity from goods_list g LEFT JOIN catalog c ON " +
                        "g.catalog_good_id=c.catalog_good_id  WHERE user_login='" + login + "'");
        PreparedStatement st = conn.prepareStatement(
                "USE proga_database;" +
                        " INSERT INTO orders (user_login, total_price, date)" +
                        "VALUES (?,?,?)");
        st.setString(1, login);
        st.setFloat(2, total_price);
        st.setString(3, date);
        st.execute();
        while (rs.next()){
            st = conn.prepareStatement(
                    " INSERT INTO order_goods (catalog_good_id,quantity) " +
                            "VALUES (?,?) ");
            st.setInt(1, rs.getInt("catalog_good_id"));
            st.setInt(2, rs.getInt("quantity"));

        }
    }
}