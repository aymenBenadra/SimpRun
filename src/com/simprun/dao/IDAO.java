package com.simprun.dao;

import java.util.ArrayList;

public interface IDAO<T> {
    boolean add(T e);

    boolean delete(T e);

    ArrayList<T> getAll();

    T get(String id);

    T getByName(String name);

    T getByTitle(String title);

    boolean update(T e);

    T getByUsername(String username);

    int count();
}
