package com.simprun.dao;

import com.simprun.model.Brief;
import com.simprun.model.IObjectable;
import com.simprun.model.Promo;
import com.simprun.model.User;

import java.util.ArrayList;

public class MemoryCollectionDriver<T extends IObjectable> implements IDriver<T> {
    private final ArrayList<T> entities;

    public MemoryCollectionDriver() {
        entities = new ArrayList<>();
    }

    public MemoryCollectionDriver(ArrayList<T> entities) {
        this.entities = entities;
    }

    public boolean add(T e) {
        return entities.add(e);
    }

    public boolean delete(T e) {
        entities.removeIf(e::equals);
        return true;
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

    @Override
    public T get(String id) {
        for (T e : entities) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }


    public T getByName(String name) {
        for (T e : entities) {
            if (e instanceof Promo) {
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
