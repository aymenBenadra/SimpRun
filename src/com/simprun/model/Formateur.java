package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Formateur extends User {
    private Promo promo;

    public Formateur(String name, String username, String email, String password) {
        super(name, username, email, password);
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
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

    @Override
    public String serializeFields() {
        return super.serializeFields() + ",promoID";
    }

    @Override
    public String serializeValues() {
        return super.serializeValues() + "," + promo.getId();
    }

    @Override
    public void accept(IDeserializeVisitor visitor, ResultSet resultSet) {
        try {
            visitor.visit(this, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
