package com.simprun.dao.local;

import com.simprun.model.Promo;

import java.util.ArrayList;

public class PromoDAO extends MemoryCollectionDAO<Promo> {
    public PromoDAO() {
        super();
    }

    public PromoDAO(ArrayList<Promo> entities) {
        super(entities);
    }

    public Promo getByName(String name) {
        for (Promo e : getAll()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
