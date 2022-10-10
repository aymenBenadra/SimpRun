package com.simprun.dao.mysql;

import com.simprun.model.Brief;
import com.simprun.visitor.DeserializeVisitor;

import java.util.ArrayList;

public class BriefDAO extends MySQLDAO<Brief> {
    public BriefDAO() {
        super("briefs");
    }

    public Brief getByTitle(String title) {
        String query = "SELECT * FROM briefs WHERE title = '" + title + "'";
        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Brief brief = new Brief();
                brief.accept(DeserializeVisitor.getInstance(), resultSet);
                return brief;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Brief> getAll() {
        ArrayList<Brief> briefs = new ArrayList<>();
        String query = "SELECT * FROM briefs";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Brief brief = new Brief();
                brief.accept(DeserializeVisitor.getInstance(), resultSet);
                briefs.add(brief);
            }
            return briefs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Brief get(String id) {
        String query = "SELECT * FROM briefs WHERE id = '" + id + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            Brief e = new Brief();
            e.accept(DeserializeVisitor.getInstance(), resultSet);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
