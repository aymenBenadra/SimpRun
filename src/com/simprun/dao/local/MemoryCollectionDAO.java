package com.simprun.dao.local;

import com.simprun.dao.IDAO;
import com.simprun.model.IObjectable;

import java.util.ArrayList;

public abstract class MemoryCollectionDAO<T extends IObjectable> implements IDAO<T> {
    private final ArrayList<T> entities;

    public MemoryCollectionDAO() {
        entities = new ArrayList<>();
    }

    public MemoryCollectionDAO(ArrayList<T> entities) {
        this.entities = entities;
    }

    public boolean add(T e) {
        return entities.add(e);
    }

    public boolean delete(T e) {
        return entities.removeIf(e::equals);
    }

    public ArrayList<T> getAll() {
        return entities;
    }

    @Override
    public T get(String id) {
        for (T e : entities) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean update(T e) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).equals(e)) {
                entities.set(i, e);
                return true;
            }
        }
        return false;
    }

    public T getByName(String name) {
        return null;
    }

    public T getByTitle(String title) {
        return null;
    }

    public T getByUsername(String username) {
        return null;
    }

    public int count() {
        return entities.size();
    }
}
