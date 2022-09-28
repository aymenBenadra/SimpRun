import java.util.UUID;

public abstract class User implements IObjectable {
    private final static int passwordEncryptionMagicNumber = 5;
    protected String id;
    protected String name;
    protected String password;
    protected String email;
    protected String username;

    public User(String name, String password, String email, String username) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = encryptPassword(password);
        this.email = email;
        this.username = username;
    }

    private String encryptPassword(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            encryptedPassword.append((char) (password.charAt(i) + passwordEncryptionMagicNumber));
        }
        return encryptedPassword.toString();
    }

    private String decryptPassword(String password) {
        StringBuilder decryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            decryptedPassword.append((char) (password.charAt(i) - passwordEncryptionMagicNumber));
        }
        return decryptedPassword.toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return decryptPassword(password);
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " {" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
