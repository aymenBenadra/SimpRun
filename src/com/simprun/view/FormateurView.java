package com.simprun.view;

import com.simprun.controlller.AuthController;
import com.simprun.controlller.FormateurController;
import com.simprun.model.BriefStatus;
import com.simprun.model.Formateur;

public class FormateurView {
    private final FormateurController formateurController;
    private final AuthController authController;
    private final TerminalUI terminalUI;
    private final Formateur formateur;

    public FormateurView(FormateurController formateurController, AuthController authController, TerminalUI terminalUI) {
        this.formateurController = formateurController;
        this.authController = authController;
        this.terminalUI = terminalUI;
        this.formateur = (Formateur) authController.getCurrentUser();
    }

    public void dashboard() {
        terminalUI.println("Formateur Dashboard: " + authController.getCurrentUser().getName());
        int choice = terminalUI.displayMenu(new String[]{
                "Manage Promo", "Manage Briefs", "Logout"
        });
        switch (choice) {
            case 1 -> managePromo();
            case 2 -> manageBriefs();
            case 3 -> logout();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void managePromo() {
        terminalUI.println("Manage Promo");
        int choice = terminalUI.displayMenu(new String[]{
                "List apprenants in promo", "Add apprenants to promo", "Remove apprenant from promo", "Back"
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
        terminalUI.displayList(formateurController.getApprenants(formateur.getPromo()));
        terminalUI.println("");
        managePromo();
    }

    private void addApprenant() {
        terminalUI.println("Add Apprenant to Promo");
        terminalUI.displayList(formateurController.getApprenants());
        String username = terminalUI.prompt("Username: ");
        if (formateurController.addApprenantToPromo(username, formateur.getPromo())) {
            terminalUI.println("Apprenant added successfully");
        } else {
            terminalUI.println("Apprenant not added");
        }
        managePromo();
    }

    private void removeApprenant() {
        terminalUI.println("Remove Apprenant from Promo");
        terminalUI.displayList(formateurController.getApprenants(formateur.getPromo()));
        String username = terminalUI.prompt("Username: ");
        if (formateurController.removeApprenantFromPromo(username)) {
            terminalUI.println("Apprenant removed successfully");
        } else {
            terminalUI.println("Apprenant not removed");
        }
        managePromo();
    }

    private void manageBriefs() {
        terminalUI.println("Manage Briefs");
        int choice = terminalUI.displayMenu(new String[]{
                "List briefs", "Add brief", "Remove brief", "Archive brief", "Back"
        });
        switch (choice) {
            case 1 -> listBriefs();
            case 2 -> addBrief();
            case 3 -> removeBrief();
            case 4 -> archiveBrief();
            case 5 -> dashboard();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void listBriefs() {
        terminalUI.println("List Briefs");
        int choice = terminalUI.displayMenu(new String[]{
                "All briefs", "Active brief", "Archived brief", "Back"
        });
        switch (choice) {
            case 1 -> listAllBriefs();
            case 2 -> listActiveBriefs();
            case 3 -> listArchivedBriefs();
            case 4 -> manageBriefs();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void listAllBriefs() {
        terminalUI.println("List All Briefs");
        terminalUI.displayList(formateurController.getBriefs());
        terminalUI.println("");
        listBriefs();
    }

    private void listActiveBriefs() {
        terminalUI.println("List Active Briefs");
        terminalUI.displayList(formateurController.getBriefs(BriefStatus.Active));
        terminalUI.println("");
        listBriefs();
    }

    private void listArchivedBriefs() {
        terminalUI.println("List Archived Briefs");
        terminalUI.displayList(formateurController.getBriefs(BriefStatus.Archived));
        terminalUI.println("");
        listBriefs();
    }

    private void addBrief() {
        terminalUI.println("Add Brief");
        String title = terminalUI.prompt("Title: ");
        String description = terminalUI.prompt("Description: ");
        String deadline = terminalUI.prompt("Deadline (YYYY-MM-DD): ");
        if (formateurController.addBrief(title, description, formateur.getPromo(), deadline)) {
            terminalUI.println("Brief added successfully");
        } else {
            terminalUI.println("Brief not added");
        }
        manageBriefs();
    }

    private void removeBrief() {
        terminalUI.println("Remove Brief");
        terminalUI.displayList(formateurController.getBriefs());
        String name = terminalUI.prompt("Name: ");
        if (formateurController.removeBrief(name)) {
            terminalUI.println("Brief removed successfully");
        } else {
            terminalUI.println("Brief not removed");
        }
        manageBriefs();
    }

    private void archiveBrief() {
        terminalUI.println("Archive Brief");
        terminalUI.displayList(formateurController.getBriefs());
        String name = terminalUI.prompt("Name: ");
        if (formateurController.archiveBrief(name)) {
            terminalUI.println("Brief archived successfully");
        } else {
            terminalUI.println("Brief not archived");
        }
        manageBriefs();
    }

    private void logout() {
        authController.logout();
        terminalUI.println("Logged out successfully");
    }
}
