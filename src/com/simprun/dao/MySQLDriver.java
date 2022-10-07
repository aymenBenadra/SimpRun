package com.simprun.dao;

import com.simprun.model.IDeserializable;
import com.simprun.model.IObjectable;
import com.simprun.model.ISerializable;
import com.simprun.visitor.DeserializeVisitor;
import io.github.cdimascio.dotenv.Dotenv;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;

public class MySQLDriver<T extends ISerializable & IObjectable & IDeserializable> implements IDriver<T> {
    private static Connection connection;
    private MemoryCollectionDriver<T> cache;
    private Statement statement;
    private ResultSet resultSet;
    private String tableName;
    private Class<T> type;

    static {
        try {
            Dotenv dotenv = Dotenv.load();
            Class.forName(dotenv.get("DATABASE_DRIVER"));
            connection = DriverManager.getConnection(dotenv.get("DATABASE_URL"),
                    dotenv.get("DATABASE_USERNAME"), dotenv.get("DATABASE_PASSWORD"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public MySQLDriver() {
        try {
            statement = connection.createStatement();
            this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
            this.tableName = type.getSimpleName().toLowerCase() + "s";
            this.cache = new MemoryCollectionDriver<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(T e) {
        String query = "INSERT INTO " + tableName + " (" + e.serializeFields() + ") VALUES (" + e.serializeValues() + ")";
        try {
            if (statement.executeUpdate(query) > 0) {
                return cache.add(e);
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(T e) {
        String query = "DELETE FROM " + tableName + " WHERE id = '" + e.getId() + "'";
        try {
            if (statement.executeUpdate(query) > 0) {
                return cache.delete(e);
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<T> getAll() {
        String query = "SELECT COUNT(*) FROM " + tableName;
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count == cache.getAll().size()) {
                return cache.getAll();
            } else {
                query = "SELECT * FROM " + tableName;
                resultSet = statement.executeQuery(query);
                ArrayList<T> list = new ArrayList<>();
                while (resultSet.next()) {
                    T e = type.getDeclaredConstructor().newInstance();
                    e.accept(DeserializeVisitor.getInstance(), resultSet);
                    list.add(e);
                }
                cache = new MemoryCollectionDriver<>(list);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T get(String id) {
        if (cache.get(id) != null) {
            return cache.get(id);
        } else {
            String query = "SELECT * FROM " + tableName + " WHERE id = '" + id + "'";
            return getT(query);
        }
    }

    @Override
    public T getByUsername(String username) {
        if (cache.getByUsername(username) != null) {
            return cache.getByUsername(username);
        } else {
            String query = "SELECT * FROM " + tableName + " WHERE username = '" + username + "'";
            return getT(query);
        }
    }

    @Override
    public T getByName(String name) {
        if (cache.getByName(name) != null) {
            return cache.getByName(name);
        } else {
            String query = "SELECT * FROM " + tableName + " WHERE name = '" + name + "'";
            return getT(query);
        }
    }

    private T getT(String query) {
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                T entity = type.getDeclaredConstructor().newInstance();
                resultSet.next();
                entity.accept(DeserializeVisitor.getInstance(), resultSet);
                cache.add(entity);
                return entity;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
