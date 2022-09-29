package com.simprun.view;

import com.simprun.controlller.AuthController;

public class AuthView {
    private final AuthController authController;
    private final TerminalUI terminalUI;

    public AuthView(AuthController authController, TerminalUI terminalUI) {
        this.authController = authController;
        this.terminalUI = terminalUI;
    }

    public void login() {
        terminalUI.println("Login");
        String username = terminalUI.prompt("Username: ");
        String password = terminalUI.prompt("Password: ");
        if (authController.login(username, password)) {
            terminalUI.println("Login successful");
        } else {
            terminalUI.println("Login failed");
        }
    }

    public void logout() {
        authController.logout();
        terminalUI.println("Logout successful");
    }
}
