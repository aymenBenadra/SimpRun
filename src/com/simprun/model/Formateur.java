package com.simprun.model;

import com.simprun.dao.MemoryCollectionDriver;

public class Formateur extends User {
    private final Promo promo;
    private final MemoryCollectionDriver<Apprenant> apprenants;
    private final MemoryCollectionDriver<Brief> briefs;

    public Formateur(String name, String password, String email, String username,
                     Promo promo, MemoryCollectionDriver<Apprenant> apprenants, MemoryCollectionDriver<Brief> briefs) {
        super(name, password, email, username);
        this.promo = promo;
        this.apprenants = apprenants;
        this.briefs = briefs;
    }

    public Promo getPromo() {
        return promo;
    }

    public MemoryCollectionDriver<Apprenant> getApprenants() {
        return apprenants;
    }

    public MemoryCollectionDriver<Brief> getBriefs() {
        return briefs;
    }

    @Override
    public String toString() {
        return "Formateur {" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", promo=" + promo.getName() + " " + promo.getYear() +
                '}';
    }
}
