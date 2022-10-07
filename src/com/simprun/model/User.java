package com.simprun.model;

import java.util.UUID;

public abstract class User implements IObjectable, ISerializable, IDeserializable {
    private final static int passwordEncryptionMagicNumber = 5;
    protected String id;
    protected String name;
    protected String password;
    protected String email;
    protected String username;

    public User(String name, String username, String email, String password) {
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


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
