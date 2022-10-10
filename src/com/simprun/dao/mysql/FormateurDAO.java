package com.simprun.dao.mysql;

import com.simprun.model.Formateur;
import com.simprun.model.UserRoles;
import com.simprun.visitor.DeserializeVisitor;

import java.util.ArrayList;

public class FormateurDAO extends MySQLDAO<Formateur> {
    private static final UserRoles role = UserRoles.Formateur;
    public FormateurDAO() {
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

    public Formateur getByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Formateur user = new Formateur();
                user.accept(DeserializeVisitor.getInstance(), resultSet);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Formateur> getAll() {
        ArrayList<Formateur> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Formateur user = new Formateur();
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
    public Formateur get(String id) {
        String query = "SELECT * FROM users WHERE id = '" + id + "'" + " AND role = '" + role + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            Formateur e = new Formateur();
            e.accept(DeserializeVisitor.getInstance(), resultSet);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
