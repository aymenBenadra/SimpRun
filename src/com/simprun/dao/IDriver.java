package com.simprun.dao;

import java.util.ArrayList;

public interface IDriver<T> {
    boolean add(T e);
    boolean delete(T e);
    ArrayList<T> getAll();
    T get(String id);
    T getByUsername(String username);
    T getByName(String name);
}
