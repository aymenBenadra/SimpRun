package com.simprun.dao;

import com.simprun.model.*;

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
        entities.removeIf(e -> e.getId().equals(id));
    }


    public T getByUsername(String username) {
        for (T e : entities) {
            if (e instanceof User) {
                if (((User) e).getUsername().equals(username)) {
                    return e;
                }
            }
        }
        return null;
    }

    public ArrayList<T> getAll() {
        return entities;
    }


    public T getByName(String name) {
        for (T e : entities) {
            if(e instanceof Promo) {
                if (((Promo) e).getName().equals(name)) {
                    return e;
                }
            } else if (e instanceof Brief) {
                if (((Brief) e).getName().equals(name)) {
                    return e;
                }
            }
        }
        return null;
    }
}
