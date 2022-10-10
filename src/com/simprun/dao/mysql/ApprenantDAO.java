package com.simprun.dao.mysql;

import com.simprun.model.Apprenant;
import com.simprun.model.UserRoles;
import com.simprun.visitor.DeserializeVisitor;

import java.util.ArrayList;

public class ApprenantDAO extends MySQLDAO<Apprenant> {
    private static final UserRoles role = UserRoles.Apprenant;

    public ApprenantDAO() {
        super("users");
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM users WHERE role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public Apprenant getByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Apprenant user = new Apprenant();
                user.accept(DeserializeVisitor.getInstance(), resultSet);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Apprenant> getAll() {
        ArrayList<Apprenant> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Apprenant user = new Apprenant();
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
    public Apprenant get(String id) {
        String query = "SELECT * FROM users WHERE id = '" + id + "'" + " AND role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            Apprenant e = new Apprenant();
            e.accept(DeserializeVisitor.getInstance(), resultSet);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
