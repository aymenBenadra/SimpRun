package com.simprun.controlller;

import com.simprun.dao.IDriver;
import com.simprun.model.Apprenant;
import com.simprun.model.Formateur;
import com.simprun.model.Promo;
import com.simprun.model.User;

public class AdminController {

    private final IDriver<Promo> promos;
    private final IDriver<Apprenant> apprenants;
    private final IDriver<Formateur> formateurs;

    public AdminController(IDriver<Promo> promos, IDriver<Apprenant> apprenants, IDriver<Formateur> formateurs) {
        this.promos = promos;
        this.apprenants = apprenants;
        this.formateurs = formateurs;
    }

    public boolean addApprenant(String name, String username, String email, String password) {
        if (apprenants.getByUsername(username) == null) {
            apprenants.add(new Apprenant(name, username, email, password));
            return true;
        }
        return false;
    }

    public boolean addFormateur(String name, String password, String email, String username) {
        if (formateurs.getByUsername(username) == null) {
            formateurs.add(new Formateur(name, username, email, password));
            return true;
        }
        return false;
    }

    public boolean addPromo(String name, int year, String formateurName) {
        Formateur formateur = formateurs.getByUsername(formateurName);
        Promo promo = new Promo(name, year, formateur);
        promos.add(promo);
        formateur.setPromo(promo);
        return true;
    }

    public boolean removeApprenant(String username) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null) {
            apprenants.delete(apprenant.getId());
            return true;
        }
        return false;
    }

    public boolean removeFormateur(String username) {
        Formateur formateur = formateurs.getByUsername(username);
        if (formateur != null) {
            formateurs.delete(formateur.getId());
            return true;
        }
        return false;
    }

    public boolean removePromo(String name) {
        Promo promo = promos.getByName(name);
        if (promo != null) {
            promos.delete(promo.getId());
            return true;
        }
        return false;
    }

    public String[] getApprenants() {
        return apprenants.getAll()
                .stream()
                .map(User::getUsername)
                .toArray(String[]::new);
    }

    public String[] getFormateurs() {
        return formateurs.getAll().stream().map(User::getUsername).toArray(String[]::new);
    }

    public String[] getPromos() {
        return promos.getAll()
                .stream()
                .map(Promo::getName)
                .toArray(String[]::new);
    }
}
