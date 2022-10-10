package com.simprun.dao.mysql;

import com.simprun.model.Apprenant;
import com.simprun.model.UserRoles;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApprenantDAO extends MySQLDAO<Apprenant> {
    private static final UserRoles role = UserRoles.Apprenant;

    public ApprenantDAO() {
        super("users", new com.simprun.dao.local.ApprenantDAO());
    }

    @Override
    public int count() {
        String query = "SELECT COUNT(*) FROM users WHERE role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public Apprenant getByUsername(String username) {
        if (cache != null) {
            Apprenant apprenant = cache.getByUsername(username);
            if (apprenant != null) {
                return apprenant;
            }
        }
        String query = "SELECT * FROM users WHERE username = ? AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getApprenant(username, statement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Apprenant> getAll() {
        if (cache != null && cache.count() == count()) {
            return cache.getAll();
        }
        ArrayList<Apprenant> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Apprenant user = new Apprenant();
                user.accept(DeserializeVisitor.getInstance(), resultSet);
                users.add(user);
            }
            cache = new com.simprun.dao.local.ApprenantDAO(users);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Apprenant get(String id) {
        if (cache != null) {
            Apprenant apprenant = cache.get(id);
            if (apprenant != null) {
                return apprenant;
            }
        }
        String query = "SELECT * FROM users WHERE id = ? AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getApprenant(id, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Apprenant getApprenant(String id, PreparedStatement statement) throws SQLException {
        statement.setString(1, id);
        statement.setString(2, role.name());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Apprenant user = new Apprenant();
            user.accept(DeserializeVisitor.getInstance(), resultSet);
            cache.add(user);
            return user;
        } else {
            return null;
        }
    }
}
