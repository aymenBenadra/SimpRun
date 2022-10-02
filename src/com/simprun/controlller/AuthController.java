package com.simprun.controlller;

import com.simprun.dao.IDriver;
import com.simprun.model.Apprenant;
import com.simprun.model.Admin;
import com.simprun.model.Formateur;
import com.simprun.model.User;

public class AuthController {

    private final IDriver<Apprenant> apprenants;
    private final IDriver<Formateur> formateurs;
    private final IDriver<Admin> admins;
    private User currentUser = null;

    public AuthController(IDriver<Admin> admins, IDriver<Formateur> formateurs, IDriver<Apprenant> apprenants) {
        this.admins = admins;
        this.formateurs = formateurs;
        this.apprenants = apprenants;
    }

    public boolean login(String username, String password) {
        User user = apprenants.getByUsername(username);
        user = user == null ? formateurs.getByUsername(username) : user;
        user = user == null ? admins.getByUsername(username) : user;
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
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
