package ru.kpfu.interfaces;

import ru.kpfu.entites.User;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Evgenia on 26.11.2017.
 */
public interface DataBaseInterface {
    void addUser(User user) throws SQLException;
    boolean checkLogin(String login) throws IOException, SQLException;
}