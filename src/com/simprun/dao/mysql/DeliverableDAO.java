package com.simprun.dao.mysql;

import com.simprun.model.Deliverable;
import com.simprun.visitor.DeserializeVisitor;

import java.util.ArrayList;

public class DeliverableDAO extends MySQLDAO<Deliverable> {
    public DeliverableDAO() {
        super("deliverables");
    }

    @Override
    public ArrayList<Deliverable> getAll() {
        ArrayList<Deliverable> deliverables = new ArrayList<>();
        String query = "SELECT * FROM deliverables";
        try {
            resultSet = statement.executeQuery(query);
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
        String query = "SELECT * FROM users WHERE id = '" + id + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            Deliverable e = new Deliverable();
            e.accept(DeserializeVisitor.getInstance(), resultSet);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
