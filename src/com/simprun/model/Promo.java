package com.simprun.model;

import java.util.ArrayList;
import java.util.UUID;

public class Promo implements IObjectable {
    private final String id;
    private final String name;
    private final int year;
    private final ArrayList<Apprenant> apprenants;
    private final ArrayList<Brief> briefs;
    private final Formateur formateur;

    public Promo(String name, int year, ArrayList<Apprenant> apprenants, ArrayList<Brief> briefs, Formateur formateur) {
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

    public ArrayList<Apprenant> getApprenants() {
        return apprenants;
    }

    public ArrayList<Brief> getBriefs() {
        return briefs;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    @Override
    public String toString() {
        return "com.simprun.model.Promo {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", formateur=" + formateur.getName() +
                '}';
    }
}
