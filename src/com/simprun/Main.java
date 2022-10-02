package com.simprun;

import com.simprun.controlller.*;
import com.simprun.dao.*;
import com.simprun.model.*;
import com.simprun.view.*;

public class Main {
    public static void main(String[] args) {
        IDriver<Apprenant> apprenants = new MemoryCollectionDriver<>();
        IDriver<Formateur> formateurs = new MemoryCollectionDriver<>();
        IDriver<Admin> admins = new MemoryCollectionDriver<>();
        IDriver<Promo> promos = new MemoryCollectionDriver<>();
        IDriver<Deliverable> deliverables = new MemoryCollectionDriver<>();
        IDriver<Brief> briefs = new MemoryCollectionDriver<>();

        AuthController authController = new AuthController(admins, formateurs, apprenants);
        TerminalUI terminalUI = new TerminalUI();
        AuthView authView = new AuthView(authController, terminalUI);
        User currentUser;

        // Seed Test Admin
        admins.add(new Admin("Admin", "admin", "admin@simp.run", "admin"));

        terminalUI.println("Welcome to Simprun");
        do {
            terminalUI.println("What do you want to do?");
            int choice = terminalUI.displayMenu(new String[]{"Login", "Exit"});
            switch (choice) {
                case 1 -> authView.login();
                case 2 -> {
                    terminalUI.println("Goodbye");
                    return;
                }
            }
            currentUser = authController.getCurrentUser();
        } while (currentUser == null);

        if (currentUser instanceof Admin) {
            AdminController adminController = new AdminController(promos, apprenants, formateurs);
            AdminView adminView = new AdminView(adminController, authController, terminalUI);
            adminView.dashboard();
        } else if (currentUser instanceof Formateur) {
            FormateurController formateurController = new FormateurController(apprenants, briefs, deliverables);
            FormateurView formateurView = new FormateurView(formateurController, authController, terminalUI);
            formateurView.dashboard();
        } else if (currentUser instanceof Apprenant) {
            ApprenantController apprenantController = new ApprenantController(briefs, deliverables);
            ApprenantView apprenantView = new ApprenantView(apprenantController, authController, terminalUI);
            apprenantView.dashboard();
        }
    }
}