package ru.kpfu.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public class DBWrapper {
    private final static String DRIVER = "org.postgresql.Driver";
    private final static String CONNECTION_URI = "jdbc:postgresql://localhost:5432/shop";
    private final static String LOGIN = "postgres";
    private final static String PASSWORD = "06tyz98";

    private static Connection conn;

    public static Connection getConection() {
        if (conn == null) {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(CONNECTION_URI, LOGIN, PASSWORD  );
            } catch (SQLException ex) {
                System.out.println("Can't connect to DB (" + ex.getErrorCode() + ": " + ex.getMessage() + ").");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}