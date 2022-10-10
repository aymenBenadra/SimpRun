package com.simprun.dao.mysql;

import com.simprun.dao.IDAO;
import com.simprun.model.IObjectable;
import com.simprun.model.ISerializable;

import java.sql.*;

public abstract class MySQLDAO<T extends IObjectable & ISerializable> implements IDAO<T> {
    protected Statement statement;
    protected ResultSet resultSet;

    public MySQLDAO() {
        try {
            statement = MySQLConnection.getConnection().createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public T getByUsername(String username) {
        return null;
    }

    public T getByTitle(String title) {
        return null;
    }

    public T getByName(String name) {
        return null;
    }
}
