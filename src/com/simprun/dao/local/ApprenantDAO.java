package com.simprun.dao.local;

import com.simprun.model.Apprenant;

import java.util.ArrayList;

public class ApprenantDAO extends MemoryCollectionDAO<Apprenant> {
    public ApprenantDAO() {
        super();
    }

    public ApprenantDAO(ArrayList<Apprenant> entities) {
        super(entities);
    }

    public Apprenant getByUsername(String username) {
        for (Apprenant e : getAll()) {
            if (e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }
}
