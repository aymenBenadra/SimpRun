package com.simprun.dao.mysql;

import com.simprun.model.Formateur;
import com.simprun.model.UserRoles;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormateurDAO extends MySQLDAO<Formateur> {
    private static final UserRoles role = UserRoles.Formateur;

    public FormateurDAO() {
        super("users");
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
        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    public Formateur getByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ? AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getFormateur(username, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Formateur> getAll() {
        ArrayList<Formateur> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();
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
        String query = "SELECT * FROM users WHERE id = ? AND role = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getFormateur(id, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Formateur getFormateur(String id, PreparedStatement statement) throws SQLException {
        statement.setString(1, id);
        statement.setString(2, role.name());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Formateur user = new Formateur();
            user.accept(DeserializeVisitor.getInstance(), resultSet);
            return user;
        } else {
            return null;
        }
    }
}
