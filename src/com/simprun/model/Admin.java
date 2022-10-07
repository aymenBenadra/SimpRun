package com.simprun.model;

public class Admin extends User{
    public Admin(String name, String username, String email, String password) {
        super(name, username, email, password);
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
}
