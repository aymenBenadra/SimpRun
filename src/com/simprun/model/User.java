package com.simprun.model;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.UUID;

public abstract class User implements IObjectable, ISerializable, IDeserializable {
    private static final Dotenv dotenv;
    protected String id;
    protected String name;
    protected String password;
    protected String email;
    protected String username;

    static {
        dotenv = Dotenv.load();
    }

    public User(String name, String username, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = encryptPassword(password);
        this.email = email;
        this.username = username;
    }

    public User() {}

    private String encryptPassword(String password) {
        StringBuilder encryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            encryptedPassword.append((char) (password.charAt(i) + Integer.parseInt(dotenv.get("PASSWORD_ENCRYPTION_MAGIC_NUMBER"))));
        }
        return encryptedPassword.toString();
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
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

    private String decryptPassword(String password) {
        StringBuilder decryptedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            decryptedPassword.append((char) (password.charAt(i) - Integer.parseInt(dotenv.get("PASSWORD_ENCRYPTION_MAGIC_NUMBER"))));
        }
        return decryptedPassword.toString();
    }
}
