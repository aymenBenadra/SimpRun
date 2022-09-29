package com.simprun.dao;

import java.util.ArrayList;

public interface IDriver<T> {
    void add(T e);
    void delete(String id);
    T get(String id);
    ArrayList<T> getAll();
    void update(String id, T e);
    int getCount();
}
