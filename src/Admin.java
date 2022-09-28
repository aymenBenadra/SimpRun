public class Admin extends User{
    public Admin(String name, String password, String email, String username) {
        super(name, password, email, username);
    }

    @Override
    public String toString() {
        return "Admin {" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}
