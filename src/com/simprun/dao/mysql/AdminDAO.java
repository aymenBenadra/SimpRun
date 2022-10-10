package com.simprun.dao.mysql;

import com.simprun.model.Admin;
import com.simprun.model.UserRoles;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO extends MySQLDAO<Admin> {
    private static final UserRoles role = UserRoles.Admin;
    public AdminDAO() {
        super("users", new com.simprun.dao.local.AdminDAO());
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM users WHERE role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public Admin getByUsername(String username) {
        if (cache != null) {
            Admin admin = cache.getByUsername(username);
            if (admin != null) {
                return admin;
            }
        }
        String query = "SELECT * FROM users WHERE username = ? AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getAdmin(username, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Admin> getAll() {
        if (cache != null && cache.count() == count()) {
            return cache.getAll();
        }
        ArrayList<Admin> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Admin user = new Admin();
                user.accept(DeserializeVisitor.getInstance(), resultSet);
                users.add(user);
            }
            cache = new com.simprun.dao.local.AdminDAO(users);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin get(String id) {
        if (cache != null) {
            Admin admin = cache.get(id);
            if (admin != null) {
                return admin;
            }
        }
        String query = "SELECT * FROM users WHERE id = ? AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getAdmin(id, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Admin getAdmin(String id, PreparedStatement statement) throws SQLException {
        statement.setString(1, id);
        statement.setString(2, role.name());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Admin user = new Admin();
            user.accept(DeserializeVisitor.getInstance(), resultSet);
            cache.add(user);
            return user;
        } else {
            return null;
        }
    }
}
