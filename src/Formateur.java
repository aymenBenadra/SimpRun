import java.util.ArrayList;

public class Formateur extends User {
    private final Promo promo;
    private final ArrayList<Apprenant> apprenants;
    private final ArrayList<Brief> briefs;

    public Formateur(String name, String password, String email, String username, Promo promo, ArrayList<Apprenant> apprenants, ArrayList<Brief> briefs) {
        super(name, password, email, username);
        this.promo = promo;
        this.apprenants = apprenants;
        this.briefs = briefs;
    }

    public Promo getPromo() {
        return promo;
    }

    public ArrayList<Apprenant> getApprenants() {
        return apprenants;
    }

    public ArrayList<Brief> getBriefs() {
        return briefs;
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
