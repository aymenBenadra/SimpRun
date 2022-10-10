package com.simprun;

import com.simprun.controlller.AdminController;
import com.simprun.controlller.ApprenantController;
import com.simprun.controlller.AuthController;
import com.simprun.controlller.FormateurController;
import com.simprun.dao.IDAO;
import com.simprun.dao.mysql.*;
import com.simprun.model.*;
import com.simprun.view.*;
import com.simprun.visitor.DeserializeVisitor;

public class Main {
    public static void main(String[] args) {
        IDAO<Apprenant> apprenants = new ApprenantDAO();
        IDAO<Formateur> formateurs = new FormateurDAO();
        IDAO<Admin> admins = new AdminDAO();
        IDAO<Promo> promos = new PromoDAO();
        IDAO<Deliverable> deliverables = new DeliverableDAO();
        IDAO<Brief> briefs = new BriefDAO();

        DeserializeVisitor.setDrivers(apprenants, formateurs, briefs, promos);

        AuthController authController = new AuthController(admins, formateurs, apprenants);
        TerminalUI terminalUI = new TerminalUI();
        AuthView authView = new AuthView(authController, terminalUI);
        User currentUser;

        terminalUI.println("Welcome to Simprun");
        while (true) {
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
}