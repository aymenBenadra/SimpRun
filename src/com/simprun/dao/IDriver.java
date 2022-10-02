package com.simprun.dao;

import java.util.ArrayList;

public interface IDriver<T> {
    void add(T e);
    void delete(String id);
    ArrayList<T> getAll();
    T getByUsername(String username);
    T getByName(String name);
}
