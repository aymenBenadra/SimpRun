package com.simprun.model;

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
}
