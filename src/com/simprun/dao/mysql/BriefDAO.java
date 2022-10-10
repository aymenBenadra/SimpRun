package com.simprun.dao.mysql;

import com.simprun.model.Brief;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BriefDAO extends MySQLDAO<Brief> {
    public BriefDAO() {
        super("briefs", new com.simprun.dao.local.BriefDAO());
    }

    public Brief getByTitle(String title) {
        if (cache != null) {
            Brief brief = cache.getByTitle(title);
            if (brief != null) {
                return brief;
            }
        }
        String query = "SELECT * FROM briefs WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getBrief(title, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Brief> getAll() {
        if (cache != null && cache.count() == count()) {
            return cache.getAll();
        }
        ArrayList<Brief> briefs = new ArrayList<>();
        String query = "SELECT * FROM briefs";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Brief brief = new Brief();
                brief.accept(DeserializeVisitor.getInstance(), resultSet);
                briefs.add(brief);
            }
            cache = new com.simprun.dao.local.BriefDAO(briefs);
            return briefs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Brief get(String id) {
        if (cache != null) {
            Brief brief = cache.get(id);
            if (brief != null) {
                return brief;
            }
        }
        String query = "SELECT * FROM briefs WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return getBrief(id, statement);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Brief getBrief(String title, PreparedStatement statement) {
        try {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Brief brief = new Brief();
                brief.accept(DeserializeVisitor.getInstance(), resultSet);
                cache.add(brief);
                return brief;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
