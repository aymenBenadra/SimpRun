package com.simprun.dao.local;

import com.simprun.model.Brief;

import java.util.ArrayList;

public class BriefDAO extends MemoryCollectionDAO<Brief> {
    public BriefDAO() {
        super();
    }

    public BriefDAO(ArrayList<Brief> entities) {
        super(entities);
    }

    public Brief getByTitle(String name) {
        for (Brief e : getAll()) {
            if (e.getTitle().equals(name)) {
                return e;
            }
        }
        return null;
    }
}
