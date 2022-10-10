package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;
import com.simprun.visitor.ISerializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Formateur extends User {
    private Promo promo;

    public Formateur(String name, String username, String email, String password) {
        super(name, username, email, password);
    }

    public Formateur() {
        super();
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
    public HashMap<String, String> accept(ISerializeVisitor visitor) {
        return visitor.visit(this);
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
