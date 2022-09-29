package com.simprun.controlller;

import com.simprun.dao.MemoryCollectionDriver;
import com.simprun.model.Apprenant;
import com.simprun.model.Brief;
import com.simprun.model.Formateur;
import com.simprun.model.Promo;

public class AdminController {

    private final MemoryCollectionDriver<Promo> promos;
    private final MemoryCollectionDriver<Apprenant> apprenants;
    private final MemoryCollectionDriver<Formateur> formateurs;

    public AdminController(MemoryCollectionDriver<Promo> promos, MemoryCollectionDriver<Apprenant> apprenants, MemoryCollectionDriver<Formateur> formateurs) {
        this.promos = promos;
        this.apprenants = apprenants;
        this.formateurs = formateurs;
    }

    public boolean addApprenant(String name, String password, String email, String username) {
        if (apprenants.getByUsername(username) == null) {
            apprenants.add(new Apprenant(name, password, email, username));
            return true;
        }
        return false;
    }

    public boolean addFormateur(String name, String password, String email, String username,
                                Promo promo, MemoryCollectionDriver<Apprenant> apprenants, MemoryCollectionDriver<Brief> briefs) {
        if (formateurs.getByUsername(username) == null) {
            formateurs.add(new Formateur(name, password, email, username, promo, apprenants, briefs));
            return true;
        }
        return false;
    }

    public boolean addPromo(String name, int year, MemoryCollectionDriver<Apprenant> apprenants,
                            MemoryCollectionDriver<Brief> briefs, Formateur formateur) {
        promos.add(new Promo(name, year, apprenants, briefs, formateur));
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
}
