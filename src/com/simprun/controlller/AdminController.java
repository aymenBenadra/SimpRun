package com.simprun.controlller;

import com.simprun.dao.MemoryCollectionDriver;
import com.simprun.model.Apprenant;
import com.simprun.model.Formateur;
import com.simprun.model.Promo;
import com.simprun.model.User;

public class AdminController {

    private final MemoryCollectionDriver<Promo> promos;
    private final MemoryCollectionDriver<Apprenant> apprenants;
    private final MemoryCollectionDriver<Formateur> formateurs;

    public AdminController(MemoryCollectionDriver<Promo> promos, MemoryCollectionDriver<Apprenant> apprenants, MemoryCollectionDriver<Formateur> formateurs) {
        this.promos = promos;
        this.apprenants = apprenants;
        this.formateurs = formateurs;
    }

    public boolean addApprenant(String name, String username, String email, String password) {
        if (apprenants.getByUsername(username) == null) {
            apprenants.add(new Apprenant(name, password, email, username));
            return true;
        }
        return false;
    }

    public boolean addFormateur(String name, String password, String email, String username) {
        if (formateurs.getByUsername(username) == null) {
            formateurs.add(new Formateur(name, password, email, username));
            return true;
        }
        return false;
    }

    public boolean addPromo(String name, int year, Formateur formateur) {
        Promo promo = new Promo(name, year, formateur);
        promos.add(promo);
        formateur.setPromo(promo);
        return true;
    }

    public boolean removeApprenant(String username) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null) {
            apprenants.delete(apprenant.getId());
        }
        return true;
    }

    public boolean removeFormateur(String username) {
        Formateur formateur = formateurs.getByUsername(username);
        if (formateur != null) {
            formateurs.delete(formateur.getId());
        }
        return true;
    }

    public boolean removePromo(String name) {
        Promo promo = promos.getByName(name);
        if (promo != null) {
            promos.delete(promo.getId());
        }
        return true;
    }

    public String[] getApprenants() {
        return apprenants.getAll().stream().map(User::getUsername).toArray(String[]::new);
    }

    public Formateur getApprenant(String username) {
        return formateurs.getAll().stream().filter(formateur -> formateur.getUsername().equals(username)).findFirst().orElse(null);
    }

    public String[] getFormateurs() {
        return formateurs.getAll().stream().map(User::getUsername).toArray(String[]::new);
    }

    public Formateur getFormateur(String username) {
        return formateurs.getAll().stream().filter(formateur -> formateur.getUsername().equals(username)).findFirst().orElse(null);
    }

    public String[] getPromos() {
        return promos.getAll().stream().map(Promo::getName).toArray(String[]::new);
    }

    public Promo getPromo(String name) {
        return promos.getAll().stream().filter(promo -> promo.getName().equals(name)).findFirst().orElse(null);
    }
}
