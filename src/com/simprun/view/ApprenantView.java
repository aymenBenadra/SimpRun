package com.simprun.view;

import com.simprun.controlller.ApprenantController;
import com.simprun.controlller.AuthController;
import com.simprun.model.Apprenant;

public class ApprenantView {
    private final ApprenantController apprenantController;
    private final AuthController authController;
    private final TerminalUI terminalUI;
    private final Apprenant apprenant;

    public ApprenantView(ApprenantController apprenantController, AuthController authController, TerminalUI terminalUI) {
        this.apprenantController = apprenantController;
        this.authController = authController;
        this.terminalUI = terminalUI;
        this.apprenant = (Apprenant) authController.getCurrentUser();
    }

    public void dashboard() {
        terminalUI.println("Apprenant Dashboard: " + authController.getCurrentUser().toString());
        int choice = terminalUI.displayMenu(new String[]{
                "Submit deliverable", "Logout"
        });
        switch (choice) {
            case 1 -> listBriefs();
            case 2 -> submitDeliverable();
            case 3 -> logout();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void listBriefs() {
        terminalUI.println("List Briefs");
        terminalUI.displayList(apprenantController.getBriefs(apprenant.getPromo()));
        terminalUI.println("");
        dashboard();
    }

    private void submitDeliverable() {
        terminalUI.println("Submit Deliverable");
        terminalUI.println("Choose brief to submit deliverable to");
        String[] briefs = apprenantController.getBriefs(apprenant.getPromo());
        int choice = terminalUI.displayMenu(briefs);
        String link = terminalUI.prompt("Deliverable link: ");
        if (apprenantController.addDeliverable(briefs[choice - 1], link, apprenant)) {
            terminalUI.println("Deliverable submitted successfully");
        } else {
            terminalUI.println("Failed to submit deliverable");
        }
        dashboard();
    }

    private void logout() {
        authController.logout();
        terminalUI.println("Logged out successfully");
    }
}
