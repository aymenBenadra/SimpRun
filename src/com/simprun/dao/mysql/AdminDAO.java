package com.simprun.dao.mysql;

import com.simprun.model.Admin;
import com.simprun.model.UserRoles;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO extends MySQLDAO<Admin> {
    private static final UserRoles role = UserRoles.Admin;
    public AdminDAO() {
        super("users");
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM users WHERE role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public Admin getByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Admin user = new Admin();
                user.accept(DeserializeVisitor.getInstance(), resultSet);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Admin> getAll() {
        ArrayList<Admin> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Admin user = new Admin();
                user.accept(DeserializeVisitor.getInstance(), resultSet);
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Admin get(String id) {
        String query = "SELECT * FROM users WHERE id = '" + id + "'" + " AND role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            Admin e = new Admin();
            e.accept(DeserializeVisitor.getInstance(), resultSet);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
