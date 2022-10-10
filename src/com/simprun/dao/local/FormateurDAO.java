package com.simprun.dao.local;

import com.simprun.model.Formateur;

import java.util.ArrayList;

public class FormateurDAO extends MemoryCollectionDAO<Formateur> {
    public FormateurDAO() {
        super();
    }

    public FormateurDAO(ArrayList<Formateur> entities) {
        super(entities);
    }

    public Formateur getByUsername(String username) {
        for (Formateur e : getAll()) {
            if (e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }
}
