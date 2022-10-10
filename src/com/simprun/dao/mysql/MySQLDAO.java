package com.simprun.dao.mysql;

import com.simprun.dao.IDAO;
import com.simprun.dao.local.MemoryCollectionDAO;
import com.simprun.model.IObjectable;
import com.simprun.model.ISerializable;
import com.simprun.visitor.SerializeVisitor;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

public abstract class MySQLDAO<T extends IObjectable & ISerializable> implements IDAO<T> {
    protected final Connection connection;
    protected final String tableName;
    protected MemoryCollectionDAO<T> cache;

    public MySQLDAO(String tableName, MemoryCollectionDAO<T> cache) {
        this.connection = MySQLConnection.getConnection();
        this.tableName = tableName;
        this.cache = cache;
    }

    public MemoryCollectionDAO<T> getLocalDAO() {
        return cache;
    }

    @Override
    public boolean add(T e) {
        HashMap<String, String> map = e.accept(SerializeVisitor.getInstance());

        String fields = String.join(",", map.keySet());
        String[] placeholders = new String[map.size()];
        Arrays.fill(placeholders, "?");

        String query = "INSERT INTO " + tableName + " (" + fields + ") VALUES (" + String.join(",", placeholders) + ")";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            int i = 1;
            for (String value : map.values()) {
                if (value == null) {
                    statement.setNull(i, Types.VARCHAR);
                } else {
                    statement.setString(i, value);
                }
                i++;
            }
            return statement.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(T e) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, e.getId());
            statement.executeUpdate();
            return statement.getUpdateCount() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean update(T e) {
        HashMap<String, String> map = e.accept(SerializeVisitor.getInstance());
        String id = map.remove("id");
        StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");
        for (String key : map.keySet()) {
            query.append(key).append(" = ?, ");
        }
        query = new StringBuilder(query.substring(0, query.length() - 2));
        query.append(" WHERE id = ?");
        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int i = 1;
            for (String value : map.values()) {
                if (value == null) {
                    statement.setNull(i, Types.VARCHAR);
                } else {
                    statement.setString(i, value);
                }
                i++;
            }
            statement.setString(i, id);
            return statement.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
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
