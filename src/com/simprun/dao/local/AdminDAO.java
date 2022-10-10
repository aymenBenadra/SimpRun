package com.simprun.dao.local;

import com.simprun.model.Admin;

import java.util.ArrayList;

public class AdminDAO extends MemoryCollectionDAO<Admin> {
    public AdminDAO() {
        super();
    }

    public AdminDAO(ArrayList<Admin> entities) {
        super(entities);
    }

    public Admin getByUsername(String username) {
        for (Admin e : getAll()) {
            if (e.getUsername().equals(username)) {
                return e;
            }
        }
        return null;
    }
}
