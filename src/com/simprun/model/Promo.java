package com.simprun.model;

import com.simprun.dao.MemoryCollectionDriver;

import java.util.UUID;

public class Promo implements IObjectable {
    private final String id;
    private final String name;
    private final int year;
    private final MemoryCollectionDriver<Apprenant> apprenants;
    private final MemoryCollectionDriver<Brief> briefs;
    private final Formateur formateur;

    public Promo(String name, int year, MemoryCollectionDriver<Apprenant> apprenants, MemoryCollectionDriver<Brief> briefs, Formateur formateur) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.apprenants = apprenants;
        this.briefs = briefs;
        this.formateur = formateur;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public MemoryCollectionDriver<Apprenant> getApprenants() {
        return apprenants;
    }

    public MemoryCollectionDriver<Brief> getBriefs() {
        return briefs;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    @Override
    public String toString() {
        return "Promo {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", formateur=" + formateur.getName() +
                '}';
    }
}
