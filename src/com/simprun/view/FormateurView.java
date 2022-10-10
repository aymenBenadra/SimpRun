package com.simprun.view;

import com.simprun.controlller.AuthController;
import com.simprun.controlller.FormateurController;
import com.simprun.model.Brief;
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
        terminalUI.println("Formateur Dashboard: " + authController.getCurrentUser().toString());
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
        String[] apprenants = formateurController.getApprenants();
        int choice = terminalUI.displayMenu(apprenants);
        if (formateurController.addApprenantToPromo(apprenants[choice - 1], formateur.getPromo())) {
            terminalUI.println("Apprenant added successfully");
        } else {
            terminalUI.println("Apprenant not added");
        }
        managePromo();
    }

    private void removeApprenant() {
        terminalUI.println("Remove Apprenant from Promo");
        String[] apprenants = formateurController.getApprenants(formateur.getPromo());
        int choice = terminalUI.displayMenu(apprenants);
        if (formateurController.removeApprenantFromPromo(apprenants[choice - 1])) {
            terminalUI.println("Apprenant removed successfully");
        } else {
            terminalUI.println("Apprenant not removed");
        }
        managePromo();
    }

    private void manageBriefs() {
        terminalUI.println("Manage Briefs");
        int choice = terminalUI.displayMenu(new String[]{
                "List briefs", "Add brief", "Show brief details", "Remove brief", "Archive brief", "Activate Brief", "Back"
        });
        switch (choice) {
            case 1 -> listBriefs();
            case 2 -> addBrief();
            case 3 -> showBriefDetails();
            case 4 -> removeBrief();
            case 5 -> archiveBrief();
            case 6 -> activateBrief();
            case 7 -> dashboard();
            default -> terminalUI.println("Invalid choice");
        }
    }

    private void activateBrief() {
        terminalUI.println("Activate Brief");
        String[] briefs = formateurController.getBriefs(BriefStatus.Archived);
        int choice = terminalUI.displayMenu(briefs);
        if (formateurController.activateBrief(briefs[choice - 1])) {
            terminalUI.println("Brief activated successfully");
        } else {
            terminalUI.println("Brief not activated");
        }
        manageBriefs();
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
        Brief brief = formateurController.addBrief(title, description, formateur.getPromo(), deadline);
        if (brief != null) {
            terminalUI.println("Brief added successfully");
            terminalUI.println("Do you want to broadcast it to all students in Promo?");
            int choice = terminalUI.displayMenu(new String[]{"Yes", "No"});
            if (choice == 1) {
                if (formateurController.broadcastBrief(brief)) {
                    terminalUI.println("Brief broadcasted successfully");
                } else {
                    terminalUI.println("Brief not broadcasted");
                }
            }
        } else {
            terminalUI.println("Brief not added");
        }
        manageBriefs();
    }

    private void showBriefDetails() {
        terminalUI.println("Show Brief Details");
        String[] briefs = formateurController.getBriefs();
        int choice = terminalUI.displayMenu(briefs);
        terminalUI.println(formateurController.getBrief(briefs[choice - 1]).toString());
        terminalUI.println("Deliverables for this brief:");
        terminalUI.displayList(formateurController.getDeliverables(briefs[choice - 1]));
        manageBriefs();
    }

    private void removeBrief() {
        terminalUI.println("Remove Brief");
        String[] briefs = formateurController.getBriefs();
        int choice = terminalUI.displayMenu(briefs);
        if (formateurController.removeBrief(briefs[choice - 1])) {
            terminalUI.println("Brief removed successfully");
        } else {
            terminalUI.println("Brief not removed");
        }
        manageBriefs();
    }

    private void archiveBrief() {
        terminalUI.println("Archive Brief");
        String[] briefs = formateurController.getBriefs(BriefStatus.Active);
        int choice = terminalUI.displayMenu(briefs);
        if (formateurController.archiveBrief(briefs[choice - 1])) {
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
