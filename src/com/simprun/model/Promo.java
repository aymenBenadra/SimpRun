package com.simprun.model;

import java.util.UUID;

public class Promo implements IObjectable {
    private final String id;
    private final String name;
    private final int year;
    private final Formateur formateur;

    public Promo(String name, int year, Formateur formateur) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
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
