package ru.kpfu.repositories;

import ru.kpfu.entites.CatalogGood;
import ru.kpfu.models.DBWrapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class CatalogDataBase {
    private static Connection conn;
    private static ResultSet rs;

    public static List<CatalogGood> getAllGoodsFromCatalog(String type) throws SQLException {
        ArrayList<CatalogGood> catalogGoods = new ArrayList<CatalogGood>();
        conn = DBWrapper.getConection();
        ResultSet rs = null;
        rs = conn.createStatement().executeQuery("SELECT* FROM catalog WHERE type='" + type + "'");
        while (rs.next()) {
            catalogGoods.add(new CatalogGood(
                    rs.getInt("catalog_good_id"),
                    rs.getString("good_name"),
                    rs.getInt("good_price"),
                    rs.getString("good_description"),
                    rs.getString("good_img")));
        }
        return catalogGoods;
    }

    public static List<CatalogGood> getLast8GoodsFromCatalog() {
        ArrayList<CatalogGood> catalogGoods = new ArrayList<CatalogGood>();
        conn = DBWrapper.getConection();
        try {
            rs = conn.createStatement().executeQuery(
                    "SELECT  * FROM catalog  ORDER BY catalog_good_id DESC fetch first 8 rows only"
            );
            while (rs.next()) {
                catalogGoods.add(new CatalogGood(
                        rs.getInt("catalog_good_id"),
                        rs.getString("good_name"),
                        rs.getInt("good_price"),
                        rs.getString("good_description"),
                        rs.getString("good_img")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogGoods;
    }

    public static CatalogGood getInfoFromCatalog(int catalogId) throws SQLException {
        conn = DBWrapper.getConection();
        CatalogGood catalogGood = null;
        rs = conn.createStatement().executeQuery("SELECT * FROM catalog " +
                "WHERE catalog_good_id=" + catalogId);
        while (rs.next()) {
            catalogGood = new CatalogGood(
//                    rs.getInt("catalog_good_id"),
                    rs.getString("good_name"),
                    rs.getInt("good_price"),
                    rs.getString("good_description"),
                    rs.getString("good_img")
            );
        }
        return catalogGood;
    }
}