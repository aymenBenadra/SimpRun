package com.simprun.controlller;

import com.simprun.dao.IDriver;
import com.simprun.model.*;

public class ApprenantController {
    private final IDriver<Brief> briefs;
    private final IDriver<Deliverable> deliverables;

    public ApprenantController(IDriver<Brief> briefs, IDriver<Deliverable> deliverables) {
        this.briefs = briefs;
        this.deliverables = deliverables;
    }

    public String[] getBriefs(Promo promo) {
        return briefs.getAll().stream()
                .filter(brief -> brief.getPromo().equals(promo) && brief.getStatus() == BriefStatus.Active)
                .map(Brief::getName)
                .toArray(String[]::new);
    }

    public Brief getBrief(String name) {
        return briefs.getByName(name);
    }

    public boolean addDeliverable(String briefName, String link, Apprenant apprenant) {
        Brief brief = getBrief(briefName);
        if (brief != null && brief.getStatus() == BriefStatus.Active) {
            Deliverable deliverable = new Deliverable(link, brief, apprenant);
            deliverables.add(deliverable);
            return true;
        }
        return false;
    }
}
