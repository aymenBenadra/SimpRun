package com.simprun.dao.mysql;

import com.simprun.model.Promo;
import com.simprun.visitor.DeserializeVisitor;

import java.util.ArrayList;

public class PromoDAO extends MySQLDAO<Promo> {
    public PromoDAO() {
        super("promos");
    }

    @Override
    public ArrayList<Promo> getAll() {
        ArrayList<Promo> promos = new ArrayList<>();
        String query = "SELECT * FROM promos";
        try {
            resultSet = statement.executeQuery(query);
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
        String query = "SELECT * FROM promos WHERE id = '" + id + "'";
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            Promo e = new Promo();
            e.accept(DeserializeVisitor.getInstance(), resultSet);
            return e;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
