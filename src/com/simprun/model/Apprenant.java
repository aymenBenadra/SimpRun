package com.simprun.model;

import com.simprun.visitor.IDeserializeVisitor;
import com.simprun.visitor.ISerializeVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
