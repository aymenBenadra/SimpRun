package com.simprun.dao.mysql;

import com.simprun.dao.IDAO;
import com.simprun.model.IObjectable;
import com.simprun.model.ISerializable;
import com.simprun.visitor.SerializeVisitor;

import java.sql.*;
import java.util.HashMap;

public abstract class MySQLDAO<T extends IObjectable & ISerializable> implements IDAO<T> {
    protected Statement statement;
    protected ResultSet resultSet;
    protected final String tableName;

    public MySQLDAO(String tableName) {
        this.tableName = tableName;
        try {
            statement = MySQLConnection.getConnection().createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(T e) {
        HashMap<String, String> map = e.accept(SerializeVisitor.getInstance());
        String fields = String.join(",", map.keySet());
        String values = String.join(",", map.values());

        String query = "INSERT INTO " + tableName + " (" + fields + ") VALUES ('" + values + "')";
        try {
            statement.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(T e) {
        String query = "DELETE FROM " + tableName + " WHERE id = '" + e.getId() + "'";
        try {
            statement.executeUpdate(query);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
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
