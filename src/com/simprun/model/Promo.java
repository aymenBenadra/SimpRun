package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;
import com.simprun.visitor.ISerializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Promo implements IObjectable, ISerializable, IDeserializable {
    private String id;
    private String name;
    private int year;
    private Formateur formateur;

    public Promo(String name, int year, Formateur formateur) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.year = year;
        this.formateur = formateur;
    }

    public Promo() {}

    // Getters
    public String getId() {
        return id;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    // Helpers
    @Override
    public String toString() {
        return "Promo {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", formateur=" + formateur.getName() +
                '}';
    }

    @Override
    public void accept(IDeserializeVisitor visitor, ResultSet resultSet) {
        try {
            visitor.visit(this, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HashMap<String, String> accept(ISerializeVisitor visitor) {
        return visitor.visit(this);
    }
}
