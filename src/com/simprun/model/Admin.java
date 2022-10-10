package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User{
    public Admin(String name, String username, String email, String password) {
        super(name, username, email, password);
    }

    public Admin() {
        super();
    }

    @Override
    public String toString() {
        return "Admin {" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", username='" + getUsername() + '\'' +
                '}';
    }

    @Override
    public void accept(IDeserializeVisitor visitor, ResultSet resultSet) {
        try {
            visitor.visit(this, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
