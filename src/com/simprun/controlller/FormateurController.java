package com.simprun.controlller;

import com.simprun.dao.IDriver;
import com.simprun.model.*;

import java.sql.Date;

public class FormateurController {
    private final IDriver<Apprenant> apprenants;
    private final IDriver<Brief> briefs;
    private final IDriver<Deliverable> deliverables;

    public FormateurController(IDriver<Apprenant> apprenants, IDriver<Brief> briefs, IDriver<Deliverable> deliverables) {
        this.apprenants = apprenants;
        this.briefs = briefs;
        this.deliverables = deliverables;
    }

    public boolean addApprenantToPromo(String username, Promo promo) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null && promo != null) {
            apprenant.setPromo(promo);
            return true;
        }
        return false;
    }

    public boolean removeApprenantFromPromo(String username) {
        Apprenant apprenant = apprenants.getByUsername(username);
        if (apprenant != null) {
            apprenant.setPromo(null);
            return true;
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
                .map(Brief::getName)
                .toArray(String[]::new);
    }

    public String[] getBriefs() {
        return briefs.getAll().stream()
                .map(Brief::getName)
                .toArray(String[]::new);
    }

    public Brief getBrief(String name) {
        return briefs.getByName(name);
    }

    public String[] getDeliverables(String briefName) {
        Brief brief = briefs.getByName(briefName);
        if (brief != null) {
            return deliverables.getAll().stream()
                    .filter(deliverable -> deliverable.getBrief() == brief)
                    .map(deliverable -> deliverable.getApprenant().getUsername() + " - " + deliverable.getLink() + " : " + deliverable.getCreatedAt().toString())
                    .toArray(String[]::new);
        }
        return null;
    }

    public boolean addBrief(String name, String description, Promo promo, String deadline) {
        if (promo != null) {
            briefs.add(new Brief(name, description, promo, Date.valueOf(deadline)));
            return true;
        }
        return false;
    }

    public boolean removeBrief(String name) {
        Brief brief = briefs.getByName(name);
        if (brief != null) {
            briefs.delete(brief.getId());
            return true;
        }
        return false;
    }

    public boolean archiveBrief(String name) {
        Brief brief = briefs.getByName(name);
        if (brief != null) {
            brief.setStatus(BriefStatus.Archived);
            return true;
        }
        return false;
    }
}
