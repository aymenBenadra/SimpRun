package com.simprun.controlller;

import com.simprun.dao.IDAO;
import com.simprun.model.*;
import com.simprun.service.EmailService;

import java.sql.Date;

public class FormateurController {
    private final IDAO<Apprenant> apprenants;
    private final IDAO<Brief> briefs;
    private final IDAO<Deliverable> deliverables;

    public FormateurController(IDAO<Apprenant> apprenants, IDAO<Brief> briefs, IDAO<Deliverable> deliverables) {
        this.apprenants = apprenants;
        this.briefs = briefs;
        this.deliverables = deliverables;
    }

    public boolean addApprenantToPromo(String username, Promo promo) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null && promo != null) {
            apprenant.setPromo(promo);
            return apprenants.update(apprenant);
        }
        return false;
    }

    public boolean removeApprenantFromPromo(String username) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null) {
            apprenant.setPromo(null);
            return apprenants.update(apprenant);
        }
        return false;
    }

    public String[] getApprenants(Promo promo) {
        return apprenants.getAll().stream()
                .filter(apprenant -> apprenant.getPromo() == promo)
                .map(Apprenant::getUsername)
                .toArray(String[]::new);
    }

    public String[] getApprenants() {
        return apprenants.getAll().stream()
                .filter(apprenant -> apprenant.getPromo() == null)
                .map(Apprenant::getUsername)
                .toArray(String[]::new);
    }

    public String[] getBriefs(BriefStatus status) {
        return briefs.getAll().stream()
                .filter(brief -> brief.getStatus() == status)
                .map(Brief::getTitle)
                .toArray(String[]::new);
    }

    public String[] getBriefs() {
        return briefs.getAll().stream()
                .map(Brief::getTitle)
                .toArray(String[]::new);
    }

    public Brief getBrief(String name) {
        return briefs.getByTitle(name);
    }

    public String[] getDeliverables(String briefTitle) {
        Brief brief = briefs.getByTitle(briefTitle);
        if (brief != null) {
            return deliverables.getAll().stream()
                    .filter(deliverable -> deliverable.getBrief() == brief)
                    .map(deliverable -> deliverable.getApprenant().getUsername() + " - " + deliverable.getLink() + " : " + deliverable.getCreatedAt().toString())
                    .toArray(String[]::new);
        }
        return null;
    }

    public Brief addBrief(String name, String description, Promo promo, String deadline) {
        if (promo != null) {
            Brief brief = new Brief(name, description, promo, Date.valueOf(deadline));
            if(briefs.add(brief)) {
                return brief;
            }
            return null;
        }
        return null;
    }

    public boolean broadcastBrief(Brief brief) {
        String mailBody = "New brief is added by your formateur " + brief.getPromo().getFormateur().getUsername()
                + "\nBrief Title: " + brief.getTitle()
                + "\nBrief Description: " + brief.getDescription()
                + "\nBrief Deadline: " + brief.getDeadline();

        for (String apprenant : getApprenants(brief.getPromo())) {
            if (!EmailService.getInstance().sendEmail(apprenants.getByUsername(apprenant).getEmail(), "New Brief added to your promo", mailBody)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeBrief(String name) {
        Brief brief = briefs.getByTitle(name);
        if (brief != null) {
            return briefs.delete(brief);
        }
        return false;
    }

    public boolean archiveBrief(String name) {
        Brief brief = briefs.getByTitle(name);
        if (brief != null) {
            brief.setStatus(BriefStatus.Archived);
            return briefs.update(brief);
        }
        return false;
    }

    public boolean activateBrief(String name) {
        Brief brief = briefs.getByTitle(name);
        if (brief != null) {
            brief.setStatus(BriefStatus.Active);
            return briefs.update(brief);
        }
        return false;
    }
}
