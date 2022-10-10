package com.simprun.controlller;

import com.simprun.dao.IDAO;
import com.simprun.model.Apprenant;
import com.simprun.model.Formateur;
import com.simprun.model.Promo;
import com.simprun.model.User;

public class AdminController {

    private final IDAO<Promo> promos;
    private final IDAO<Apprenant> apprenants;
    private final IDAO<Formateur> formateurs;

    public AdminController(IDAO<Promo> promos, IDAO<Apprenant> apprenants, IDAO<Formateur> formateurs) {
        this.promos = promos;
        this.apprenants = apprenants;
        this.formateurs = formateurs;
    }

    public boolean addApprenant(String name, String username, String email, String password) {
        if (apprenants.getByUsername(username) == null) {
            return apprenants.add(new Apprenant(name, username, email, password));
        }
        return false;
    }

    public boolean addFormateur(String name, String password, String email, String username) {
        if (formateurs.getByUsername(username) == null) {
            return formateurs.add(new Formateur(name, username, email, password));
        }
        return false;
    }

    public boolean addPromo(String name, int year, String formateurName) {
        Formateur formateur = formateurs.getByUsername(formateurName);
        Promo promo = new Promo(name, year, formateur);
        if (promos.add(promo)) {
            formateur.setPromo(promo);
            return formateurs.update(formateur);
        }
        return false;
    }

    public boolean removeApprenant(String username) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null) {
            return apprenants.delete(apprenant);
        }
        return false;
    }

    public boolean removeFormateur(String username) {
        Formateur formateur = formateurs.getByUsername(username);
        if (formateur != null) {
            return formateurs.delete(formateur);
        }
        return false;
    }

    public boolean removePromo(String name) {
        Promo promo = promos.getByName(name);
        if (promo != null) {
            return promos.delete(promo);
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
        return formateurs.getAll()
                .stream()
                .map(User::getUsername)
                .toArray(String[]::new);
    }

    public String[] getPromos() {
        return promos.getAll()
                .stream()
                .map(Promo::getName)
                .toArray(String[]::new);
    }
}
