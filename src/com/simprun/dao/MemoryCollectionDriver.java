package com.simprun.dao;

import com.simprun.model.IObjectable;
import java.util.ArrayList;

public class MemoryCollectionDriver<T extends IObjectable> implements IDriver<T> {
    private final ArrayList<T> entities;

    public MemoryCollectionDriver() {
        entities = new ArrayList<>();
    }

    public void add(T e) {
        entities.add(e);
    }

    public void delete(String id) {
        entities.removeIf(e -> {
            return e.getId().equals(id);
        });
    }

    public T get(String id) {
        for (T e : entities) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public ArrayList<T> getAll() {
        return entities;
    }

    public void update(String id, T e) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId().equals(id)) {
                entities.set(i, e);
            }
        }
    }

    public int getCount() {
        return entities.size();
    }
}
