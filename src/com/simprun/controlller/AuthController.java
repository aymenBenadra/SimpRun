package com.simprun.controlller;

import com.simprun.dao.MemoryCollectionDriver;
import com.simprun.model.User;

public class AuthController {

    private final MemoryCollectionDriver<User> users;
    private User currentUser = null;

    public AuthController(MemoryCollectionDriver<User> users) {
        this.users = users;
    }

    public boolean login(String username, String password) {
        for (User user : users.getAll()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}
