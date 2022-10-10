package com.simprun.dao.mysql;

import com.simprun.model.Deliverable;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DeliverableDAO extends MySQLDAO<Deliverable> {
    public DeliverableDAO() {
        super("deliverables");
    }

    @Override
    public ArrayList<Deliverable> getAll() {
        ArrayList<Deliverable> deliverables = new ArrayList<>();
        String query = "SELECT * FROM deliverables";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Deliverable deliverable = new Deliverable();
                deliverable.accept(DeserializeVisitor.getInstance(), resultSet);
                deliverables.add(deliverable);
            }
            return deliverables;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Deliverable get(String id) {
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Deliverable deliverable = new Deliverable();
                deliverable.accept(DeserializeVisitor.getInstance(), resultSet);
                return deliverable;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
