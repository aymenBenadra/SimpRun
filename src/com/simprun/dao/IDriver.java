package com.simprun.dao;

import java.util.ArrayList;

public interface IDriver<T> {
    void add(T o);
    void delete(String id);
    Object get(String id);
    ArrayList<T> getAll();
    void update(String id, T o);
    int getCount();
}
