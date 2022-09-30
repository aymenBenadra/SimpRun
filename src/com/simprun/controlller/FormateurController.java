package com.simprun.controlller;

import com.simprun.dao.MemoryCollectionDriver;
import com.simprun.model.Apprenant;
import com.simprun.model.Brief;
import com.simprun.model.BriefStatus;
import com.simprun.model.Promo;

import java.sql.Date;

public class FormateurController {
    private final MemoryCollectionDriver<Apprenant> apprenants;
    private final MemoryCollectionDriver<Brief> briefs;

    public FormateurController(MemoryCollectionDriver<Apprenant> apprenants, MemoryCollectionDriver<Brief> briefs) {
        this.apprenants = apprenants;
        this.briefs = briefs;
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
