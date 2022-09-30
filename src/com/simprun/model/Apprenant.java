package com.simprun.model;

public class Apprenant extends User{
    private Formateur formateur;
    private Promo promo;

    public Apprenant(String name, String username, String email, String password) {
        super(name, username, email, password);
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

    public Formateur getFormateur() {
        return formateur;
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
                ", formateur=" + formateur.getName() +
                '}';
    }
}
