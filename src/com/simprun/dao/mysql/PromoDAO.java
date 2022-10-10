package com.simprun.dao.mysql;

import com.simprun.model.Promo;
import com.simprun.visitor.DeserializeVisitor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class PromoDAO extends MySQLDAO<Promo> {
    public PromoDAO() {
        super("promos");
    }

    @Override
    public ArrayList<Promo> getAll() {
        ArrayList<Promo> promos = new ArrayList<>();
        String query = "SELECT * FROM promos";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Promo promo = new Promo();
                promo.accept(DeserializeVisitor.getInstance(), resultSet);
                promos.add(promo);
            }
            return promos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Promo get(String id) {
        String query = "SELECT * FROM promos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Promo promo = new Promo();
                promo.accept(DeserializeVisitor.getInstance(), resultSet);
                return promo;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
