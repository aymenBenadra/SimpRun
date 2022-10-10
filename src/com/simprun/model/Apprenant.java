package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Apprenant extends User{
    private Promo promo;

    public Apprenant(String name, String username, String email, String password) {
        super(name, username, email, password);
    }

    public Apprenant() {
        super();
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public Promo getPromo() {
        return promo;
    }

    @Override
    public String toString() {
        return "Apprenant {" +
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
        return super.serializeValues() + "," + (promo != null ? promo.getId() : "NULL");
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
