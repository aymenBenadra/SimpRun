package com.simprun.dao.mysql;

import com.simprun.model.Deliverable;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DeliverableDAO extends MySQLDAO<Deliverable> {
    public DeliverableDAO() {
        super("deliverables", new com.simprun.dao.local.DeliverableDAO());
    }

    @Override
    public ArrayList<Deliverable> getAll() {
        if (cache != null && cache.count() == count()) {
            return cache.getAll();
        }
        ArrayList<Deliverable> deliverables = new ArrayList<>();
        String query = "SELECT * FROM deliverables";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Deliverable deliverable = new Deliverable();
                deliverable.accept(DeserializeVisitor.getInstance(), resultSet);
                deliverables.add(deliverable);
            }
            cache = new com.simprun.dao.local.DeliverableDAO(deliverables);
            return deliverables;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Deliverable get(String id) {
        if (cache != null) {
            Deliverable deliverable = cache.get(id);
            if (deliverable != null) {
                return deliverable;
            }
        }
        String query = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Deliverable deliverable = new Deliverable();
                deliverable.accept(DeserializeVisitor.getInstance(), resultSet);
                cache.add(deliverable);
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
