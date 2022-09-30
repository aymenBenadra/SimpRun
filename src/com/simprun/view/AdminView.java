package com.simprun.view;

import com.simprun.controlller.AdminController;
import com.simprun.controlller.AuthController;

public class AdminView {
    private final AdminController adminController;
    private final AuthController authController;
    private final TerminalUI terminalUI;

    public AdminView(AdminController adminController, AuthController authController, TerminalUI terminalUI) {
        this.adminController = adminController;
        this.authController = authController;
        this.terminalUI = terminalUI;
    }

    public void dashboard() {
        terminalUI.println("Admin Dashboard: " + authController.getCurrentUser().getName());
        int choice = terminalUI.displayMenu(new String[]{
                "Manage Apprenants", "Manage Formateurs", "Manage Promos", "Logout"
        });
        switch (choice) {
            case 1 -> manageApprenants();
            case 2 -> manageFormateurs();
            case 3 -> managePromos();
            case 4 -> logout();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void manageApprenants() {
        terminalUI.println("Manage Apprenants");
        int choice = terminalUI.displayMenu(new String[]{
                "List Apprenants", "Add Apprenant", "Remove Apprenant", "Back"
        });
        switch (choice) {
            case 1 -> listApprenants();
            case 2 -> addApprenant();
            case 3 -> removeApprenant();
            case 4 -> dashboard();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void listApprenants() {
        terminalUI.println("List Apprenants");
        terminalUI.displayList(adminController.getApprenants());
        terminalUI.println("");
        manageApprenants();
    }

    private void addApprenant() {
        terminalUI.println("Add Apprenant");
        String name = terminalUI.prompt("Name: ");
        String username = terminalUI.prompt("Username: ");
        String email = terminalUI.prompt("Email: ");
        String password = terminalUI.prompt("Password: ");
        if (adminController.addApprenant(name, username, email, password)) {
            terminalUI.println("Apprenant added successfully");
        } else {
            terminalUI.println("Failed to add apprenant");
        }
        manageApprenants();
    }

    private void removeApprenant() {
        terminalUI.println("Remove Apprenant");
        terminalUI.displayList(adminController.getApprenants());
        String username = terminalUI.prompt("Apprenant username: ");
        if (adminController.removeApprenant(username)) {
            terminalUI.println("Apprenant removed successfully");
        } else {
            terminalUI.println("Failed to remove apprenant");
        }
        manageApprenants();
    }

    private void manageFormateurs() {
        terminalUI.println("Manage Formateurs");
        int choice = terminalUI.displayMenu(new String[]{
                "List Formateurs", "Add Formateur", "Remove Formateur", "Back"
        });
        switch (choice) {
            case 1 -> listFormateurs();
            case 2 -> addFormateur();
            case 3 -> removeFormateur();
            case 4 -> dashboard();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void listFormateurs() {
        terminalUI.println("List Formateurs");
        terminalUI.displayList(adminController.getFormateurs());
        terminalUI.println("");
        manageFormateurs();
    }

    private void addFormateur() {
        terminalUI.println("Add Formateur");
        String name = terminalUI.prompt("Name: ");
        String username = terminalUI.prompt("Username: ");
        String email = terminalUI.prompt("Email: ");
        String password = terminalUI.prompt("Password: ");
        if (adminController.addFormateur(name, username, email, password)) {
            terminalUI.println("Formateur added successfully");
        } else {
            terminalUI.println("Failed to add formateur");
        }
        manageFormateurs();
    }

    private void removeFormateur() {
        terminalUI.println("Remove Formateur");
        terminalUI.displayList(adminController.getFormateurs());
        String username = terminalUI.prompt("Formateur username: ");
        if (adminController.removeFormateur(username)) {
            terminalUI.println("Formateur removed successfully");
        } else {
            terminalUI.println("Failed to remove formateur");
        }
        manageFormateurs();
    }

    private void managePromos() {
        terminalUI.println("Manage Promos");
        int choice = terminalUI.displayMenu(new String[]{
                "List Promos", "Add Promo", "Remove Promo", "Back"
        });
        switch (choice) {
            case 1 -> listPromos();
            case 2 -> addPromo();
            case 3 -> removePromo();
            case 4 -> dashboard();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void listPromos() {
        terminalUI.println("List Promos");
        terminalUI.displayList(adminController.getPromos());
        terminalUI.println("");
        managePromos();
    }

    private void addPromo() {
        terminalUI.println("Add Promo");
        String name = terminalUI.prompt("Name: ");
        int year = terminalUI.promptInt("Year: ", 2000, 2099);
        terminalUI.println("Formateurs:");
        terminalUI.displayList(adminController.getFormateurs());
        if (adminController.addPromo(name, year, adminController.getFormateur(terminalUI.prompt("Formateur username: ")))) {
            terminalUI.println("Promo added successfully");
        } else {
            terminalUI.println("Failed to add promo");
        }
        managePromos();
    }

    private void removePromo() {
        terminalUI.println("Remove Promo");
        String name = terminalUI.prompt("Name: ");
        if (adminController.removePromo(name)) {
            terminalUI.println("Promo removed successfully");
        } else {
            terminalUI.println("Failed to remove promo");
        }
        managePromos();
    }

    private void logout() {
        authController.logout();
        terminalUI.println("Logged out successfully");
    }

}
